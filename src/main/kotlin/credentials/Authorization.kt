package credentials

import OsuKDK
import enums.GrantType
import enums.ScopesEnum
import exceptions.AuthorizationException
import exceptions.InformationNotFoundException
import exceptions.ScopeMissingException
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.json.JsonPrimitive
import org.slf4j.LoggerFactory
import java.awt.Desktop
import java.net.InetAddress
import java.net.ServerSocket
import java.net.URI
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class Authorization(
    val clientId: Int,
    val clientSecret: String,
    val redirectUri: String? = null,
    val scopes: List<ScopesEnum> = listOf(ScopesEnum.PUBLIC),
    var grantType: GrantType = GrantType.CLIENT_CREDENTIALS,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    apiVersion: Int = 20240529
) {
    private val DOMAIN = "osu.ppy.sh"
    private val AUTH_URL = "https://$DOMAIN/oauth"
    private val stateUuid = UUID.randomUUID()
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    private suspend fun getCredentialsGrant(): Credentials {
        val request = HttpClient(CIO)

        val credentials = createRequestBody().let {
            val response = request.post("${AUTH_URL}/token") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(it))
            }
            json.decodeFromString<Credentials>(response.bodyAsText()).also {
                logger.info("New credentials were obtained")
            }
        }

        return credentials
    }

    private fun authorize() {
        val authorizationUrlQuery = parameters {
            append("client_id", clientId.toString())
            append("response_type", "code")
            append("redirect_uri", redirectUri!!)
            append("state", stateUuid.toString())
            append("scope", scopes.joinToString(separator = " ") { it.value })
        }
        val uri = URI.create("$AUTH_URL/authorize?${authorizationUrlQuery.formUrlEncode()}")

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop().browse(uri)
        else
            logger.info("Please open this link in your browser: $uri")
        logger.info("Creating authorization url with scopes: $scopes")
    }

    private fun getAuthorizationCode(): String {
        try {
            return authorize().let {
                val params = URI(redirectUri!!).let {
                    ServerSocket(it.port, 0, InetAddress.getByName(it.host))
                        .accept().use { request ->
                            val reader = request.getInputStream().bufferedReader().readLine().split(" ")[1]
                            val redirectParams = URI("http://${it.host}:${it.port}${reader}")
                            val params = redirectParams.query.split("&").associate { params ->
                                val (key, value) = params.split("=")
                                key to value
                            }
                            request.getOutputStream().write("We have received your authorization, you can close this window now.".toByteArray())
                            logger.info("Getting authentication code")

                            params
                        }
                }
                if (UUID.fromString(params.get("state")) != stateUuid) {
                    error("CSRF detected: state mismatch!")
                }
                params.get("code")!!
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun createRequestBody(): Map<String, JsonElement> {
        var body = mapOf(
            "client_id" to JsonPrimitive(clientId),
            "client_secret" to JsonPrimitive(clientSecret),
            "scope" to JsonPrimitive(ScopesEnum.PUBLIC.value),
            "grant_type" to JsonPrimitive(grantType.value)
        )
        if (grantType == GrantType.AUTHORIZATION_CODE || !redirectUri.isNullOrEmpty()) {
            body = body.plus(mapOf(
                "code" to JsonPrimitive(getAuthorizationCode()),
                "redirect_uri" to JsonPrimitive(redirectUri),
                "grant_type" to JsonPrimitive(GrantType.AUTHORIZATION_CODE.value.also {
                    grantType = GrantType.AUTHORIZATION_CODE
                })
            ))
        }
        logger.info("Creating request body with grant $grantType and scopes $scopes")

        return body
    }

    private val client = HttpClient(CIO) {
        defaultRequest {
            header("x-api-version", apiVersion)
        }

        install(ContentNegotiation) {
            json
        }

        install(Logging) {
            level = LogLevel.INFO
            logger = Logger.DEFAULT

            filter { it.url.host.contains("io.ktor") }
            sanitizeHeader { it == HttpHeaders.Authorization }
        }

        install(Auth) {
            bearer {
                if (!accessToken.isNullOrEmpty()) {
                    logger.info("Trying to use the access token directly")
                    loadTokens {
                        BearerTokens(accessToken, refreshToken)
                    }
                }

                refreshTokens {
                    getCredentialsGrant().let {
                        BearerTokens(
                            if (it.accessToken.isNullOrEmpty()) accessToken ?: ""
                            else it.accessToken, it.refreshToken
                        )
                    }
                }
            }
        }

        HttpResponseValidator {
            validateResponse { response ->
                logger.info("Response (${response.status.value}) from: ${response.request.url}")

                when(response.status) {
                    HttpStatusCode.Unauthorized -> throw AuthorizationException()
                    HttpStatusCode.Forbidden -> throw ScopeMissingException(scopes)
                    HttpStatusCode.NotFound -> throw InformationNotFoundException(response.request.url.toString())
                }
            }
        }
    }

    fun create(): OsuKDK {
        return OsuKDK(client)
    }
}