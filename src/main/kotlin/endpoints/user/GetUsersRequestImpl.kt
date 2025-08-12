package endpoints.user

import OsuKDK
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.users.GetUsersResponse
import models.users.User

class GetUsersRequestImpl(val ids: List<String>, val includeVariantStatistics: Boolean?) : EndpointRequest<List<User>> {

    override fun endpoint(): String = "users/"

    override suspend fun request(client: HttpClient): List<User> {
        val response = client.get(this.url) {
            ids.forEach { parameter("ids[]", it) }
            parameter("include_variant_statistics", includeVariantStatistics)
        }
        return OsuKDK.json.decodeFromString<GetUsersResponse>(response.bodyAsText()).users
    }
}