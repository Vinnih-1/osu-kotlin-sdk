package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.Forum

class GetForumListingRequestImpl : EndpointRequest<List<Forum>> {

    override fun endpoint(): String = "forums"

    override suspend fun request(client: HttpClient): List<Forum> {
        val response = client.get(this.url)
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject["forums"]
        return json.decodeFromString<List<Forum>>(jsonObject?.jsonArray.toString())
    }
}