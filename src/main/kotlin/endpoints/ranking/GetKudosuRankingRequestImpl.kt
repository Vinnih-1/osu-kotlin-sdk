package endpoints.ranking

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.users.User

class GetKudosuRankingRequestImpl(val page: Int?) : EndpointRequest<List<User>> {

    override fun endpoint(): String = "rankings/kudosu"

    override suspend fun request(client: HttpClient): List<User> {
        val response = client.get(this.url) {
            parameter("page", page)
        }
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject
        return json.decodeFromString<List<User>>(jsonObject["ranking"]?.jsonArray.toString())
    }
}