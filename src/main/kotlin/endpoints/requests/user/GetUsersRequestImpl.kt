package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.users.UsersResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetUsersRequestImpl(val ids: List<String>, val includeVariantStatistics: Boolean?) : EndpointRequest<UsersResponse> {

    override fun endpoint(): String = "users/"

    override suspend fun request(client: HttpClient): UsersResponse {
        val response = client.get(this.url) {
            ids.forEach { parameter("ids[]", it) }
            parameter("include_variant_statistics", includeVariantStatistics)
        }
        return json.decodeFromString<UsersResponse>(response.bodyAsText())
    }
}