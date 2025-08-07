package endpoints.user

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.KudosuHistory

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