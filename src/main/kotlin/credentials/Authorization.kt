package credentials

import OsuKDK
import enums.GrantType
import enums.ScopesEnum
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
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
    val clientId: Int? = null,
    val clientSecret: String? = null,
    val redirectUri: String? = null,
    val scope: List<ScopesEnum> = listOf(ScopesEnum.PUBLIC),
    val grantType: GrantType = GrantType.CLIENT_CREDENTIALS,
    var accessToken: String? = null,
    var refreshToken: String? = null,
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

    private suspend fun getCredentialsGrant(): Credentials? {
        if (clientId == null || clientSecret == null) return null
        val request = HttpClient(CIO)

        val credentials = createRequestBody().let {
            val response = request.post("${AUTH_URL}/token") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(it))
            }
            json.decodeFromString<Credentials>(response.bodyAsText()).also { credentials ->
                this.accessToken = credentials.accessToken
                this.refreshToken = credentials.refreshToken
            }
        }

        return credentials
    }

    private suspend fun authorize() {
        client.get("${AUTH_URL}/authorize") {
            parameter("client_id", clientId)
            parameter("response_type", "code")
            parameter("redirect_uri", redirectUri)
            parameter("state", stateUuid)
            parameter("scope", scope.joinToString(separator = " ") { it.value })
        }.also {
            val host = it.request.url.protocolWithAuthority
            val pathAndQuery = it.request.url.encodedPathAndQuery
            val uri = URI.create("$host$pathAndQuery")

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri)
            } else {
                logger.info("Please open this link in your browser: $uri")
            }
        }
    }

    private suspend fun getAuthorizationCode(): String {
        try {
            return authorize().let {
                val params = URI(redirectUri).let {
                    ServerSocket(it.port, 0, InetAddress.getByName(it.host))
                        .accept().use { request ->
                            val reader = request.getInputStream().bufferedReader().readLine().split(" ")[1]
                            val redirectParams = URI("http://${it.host}:${it.port}${reader}")
                            val params = redirectParams.query.split("&").associate { params ->
                                val (key, value) = params.split("=")
                                key to value
                            }
                            request.getOutputStream().write("We have received your authorization, you can close this window now.".toByteArray())

                            params
                        }
                }
                if (UUID.fromString(params.get("state")) != stateUuid) {
                    error("CSRF detected: state mismatch!")
                }
                params.get("code") as String
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private suspend fun createRequestBody(): Map<String, JsonElement> {
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
                "grant_type" to JsonPrimitive(GrantType.AUTHORIZATION_CODE.value)
            ))
        }

        return body
    }

    private val client = HttpClient(CIO) {
        defaultRequest {
            header("x-api-version", apiVersion)
        }

        install(Logging) {
            level = LogLevel.NONE
            logger = Logger.DEFAULT
        }

        install(Auth) {
            bearer {
                if (!accessToken.isNullOrEmpty()) {
                    loadTokens {
                        BearerTokens(accessToken as String, refreshToken)
                    }
                }

                refreshTokens {
                    getCredentialsGrant().let {
                        BearerTokens(
                            if (it?.accessToken.isNullOrEmpty()) accessToken as String else it.accessToken,
                            if (it?.refreshToken.isNullOrEmpty()) refreshToken else it.refreshToken
                        )
                    }
                }
            }
        }
    }

    fun create(): OsuKDK {
        return OsuKDK(client)
    }
}