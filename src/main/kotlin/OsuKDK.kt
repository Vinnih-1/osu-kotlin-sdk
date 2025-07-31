import credentials.Credentials
import endpoints.user.GetUserRequestsImpl
import endpoints.user.GetUsersRequestImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import models.User

@OptIn(ExperimentalSerializationApi::class)
class OsuKDK(val credentials: Credentials) {

    val client = HttpClient {
        defaultRequest {
            header(HttpHeaders.Authorization, "${credentials.tokenType} ${credentials.accessToken}")
        }
    }

    companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            explicitNulls = true
            namingStrategy = JsonNamingStrategy.Builtins.SnakeCase
        }
    }

    suspend fun getUser(userId: Int, mode: ModeEnum = ModeEnum.OSU): User {
        return GetUserRequestsImpl(userId, mode).request(client)
    }

    suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean? = true): List<User> {
        return GetUsersRequestImpl(ids, includeVariantStatistics).request(client)
    }
}