package credentials

import OsuKDK
import enums.GrantType
import enums.Scopes
import enums.Version
import exceptions.AuthenticationException
import exceptions.InformationNotFoundException
import exceptions.ScopeMissingException
import exceptions.TooManyRequestsException
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
import sdk.Interop
import sdk.OsuSDK
import sdk.OsuSDKSync
import java.awt.Desktop
import java.net.InetAddress
import java.net.ServerSocket
import java.net.URI
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
class Auth(
    val clientId: Int,
    val clientSecret: String,
    var redirectUri: String? = null,
    var scopes: List<Scopes> = listOf(Scopes.PUBLIC),
    var grantType: GrantType = GrantType.CLIENT_CREDENTIALS,
    var accessToken: String? = null,
    var refreshToken: String? = null,

    var version: Version = Version.V2025_04_10
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

    /**
     * takes the body created by the method below and makes the request
     * to get the access token and the refresh token (if grant type is AUTHORIZATION_GRANT)
     * and transform them into Credentials.
     */
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

    /**
     * Create an authorization url similar to
     * "https://osu.ppy.sh/oauth/authorize?client_id=CLIENT_ID&response_type=code&redirect_uri=REDIRECT_URI&state=STATE&scope=public"
     *
     * This url will be necessary to authorize the application to make some requests with AUTHORIZATION_GRANT
     *
     * @see getAuthorizationCode for more information.
     */
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
        logger.info("Please open this link in your browser: $uri")
        logger.info("Creating authorization url with scopes: $scopes")
    }

    /**
     * Create a temporary websocket to get the code from authorization url
     * and check if the State uuid has not been violated. If not
     * this authorization code will be used to get an AUTHORIZATION_GRANT.
     *
     * @see getCredentialsGrant
     */
    private fun getAuthorizationCode(): String {
        try {
            return authorize().let {
                var params: Map<String, String>? = null
                URI(redirectUri!!).let {
                    val serverSocket = ServerSocket(it.port, 0, InetAddress.getByName(it.host))
                    while(params == null) {
                        serverSocket.accept().use { request ->
                            val reader = request.getInputStream().bufferedReader().readLine().split(" ")[1]
                            val redirectParams = URI("http://${it.host}:${it.port}${reader}")

                            if (redirectParams.query == null) return@use
                            params = redirectParams.query.split("&").associate { params ->
                                val (key, value) = params.split("=")
                                key to value
                            }
                            request.getOutputStream().write("We have received your authorization, you can close this window now.".toByteArray())
                            logger.info("Getting authentication code")
                        }
                    }
                    serverSocket.close()
                }
                if (UUID.fromString(params?.get("state")) != stateUuid) error("CSRF detected: state mismatch!")
                params?.get("code")!!
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    /**
     * This method creates a JSON body to make the request to get an AUTHORIZATION_GRANT
     * and changes the grant type of the class if redirectUri is specified.
     *
     * @see getCredentialsGrant
     */
    private fun createRequestBody(): Map<String, JsonElement> {
        var body = mapOf(
            "client_id" to JsonPrimitive(clientId),
            "client_secret" to JsonPrimitive(clientSecret),
            "scope" to JsonPrimitive(Scopes.PUBLIC.value),
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
            header("x-api-version", version.value)
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
                        BearerTokens(accessToken!!, refreshToken)
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
                    HttpStatusCode.Unauthorized -> throw AuthenticationException()
                    HttpStatusCode.Forbidden -> throw ScopeMissingException(scopes)
                    HttpStatusCode.NotFound -> throw InformationNotFoundException(response.request.url.toString())
                    HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
                }
            }
        }
    }

    /**
     * Call this method if you are using Kotlin.
     * If you are using Java, use createSync() instead.
     *
     * @see createSync
     */
    fun createAsync(): OsuSDK {
        return OsuKDK(client)
    }

    /**
     * This method will take all of OsuKDK methods and make it synchronous.
     * Since Java does not support suspend functions, they need to be synchronous
     * to be called using Java.
     *
     * @see Interop.makeSync
     */
    fun createSync(): OsuSDKSync {
        return Interop.makeSync(createAsync())
    }
}