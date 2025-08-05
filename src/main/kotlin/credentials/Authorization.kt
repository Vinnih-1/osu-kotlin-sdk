package credentials

import OsuKDK
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import java.awt.Desktop
import java.net.InetAddress
import java.net.ServerSocket
import java.net.URI
import java.util.*

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Authorization(
    val clientId: Int,
    val clientSecret: String,
    var code: String = "",
    val grantType: GrantType = GrantType.CLIENT_CREDENTIALS,
    val scope: List<ScopesEnum> = listOf(ScopesEnum.PUBLIC),
    val redirectUri: String = "http://localhost:3914"
) {

    companion object {
        val client = HttpClient(CIO)
        const val DOMAIN = "osu.ppy.sh"

        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            encodeDefaults = true
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
    }

    private suspend fun authorizeApplication(): String {
        val stateUuid = UUID.randomUUID().toString()
        val response = client.get("https://$DOMAIN/oauth/authorize") {
            parameter("client_id", clientId)
            parameter("response_type", "code")
            parameter("redirect_uri", redirectUri)
            parameter("state", stateUuid)
            parameter("scope", scope.joinToString(separator = " ") { it.name.lowercase() })
        }
        val host = response.request.url.protocolWithAuthority
        val pathAndQuery = response.request.url.encodedPathAndQuery

        Desktop.getDesktop().browse(URI.create("$host$pathAndQuery"))

        val uri = URI(redirectUri)
        val server = ServerSocket(uri.port, 0, InetAddress.getByName(uri.host))
        val accept = server.accept()

        val input = accept.inputStream.bufferedReader().readLine()
        val code = input.substringAfter("code=").substringBefore("&state=")
        val state = input.substringAfter("state=").substringBefore(" HTTP")

        if (state != stateUuid) {
            error("CSRF detected: state mismatch!")
        }
        accept.outputStream.write("We have received your authorization, you can close this window now.".toByteArray())

        accept.close()
        server.close()

        return code
    }

    suspend fun create(): OsuKDK {
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            this.code = authorizeApplication()
        }
        val response = client.post("https://$DOMAIN/oauth/token") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(serializer(), this@Authorization))
        }
        val credentials = json.decodeFromString<Credentials>(response.bodyAsText())
        return OsuKDK(credentials)
    }
}