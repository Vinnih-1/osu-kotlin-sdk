package endpoints.ranking

import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import models.Spotlight
import OsuKDK.Companion.json
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class GetSpotlightsRequestImpl : EndpointRequest<List<Spotlight>> {

    override fun endpoint(): String = "spotlights"

    override suspend fun request(client: HttpClient): List<Spotlight> {
        val response = client.get(this.url)
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject
        println(response.bodyAsText())
        return json.decodeFromString<List<Spotlight>>(jsonObject["spotlights"]?.jsonArray.toString())
    }
}