package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.users.KudosuHistory

class GetUserKudosuRequestImpl(val userId: Int, val limit: Int?, val offset: String?) : EndpointRequest<List<KudosuHistory>> {

    override fun endpoint(): String = "users/${userId}/kudosu/"

    override suspend fun request(client: HttpClient): List<KudosuHistory> {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("offset", offset)
        }
        return json.decodeFromString<List<KudosuHistory>>(response.bodyAsText())
    }
}