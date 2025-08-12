package endpoints.ranking

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.rankings.Spotlight

class GetSpotlightsRequestImpl : EndpointRequest<List<Spotlight>> {

    override fun endpoint(): String = "spotlights"

    override suspend fun request(client: HttpClient): List<Spotlight> {
        val response = client.get(this.url)
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject
        println(response.bodyAsText())
        return json.decodeFromString<List<Spotlight>>(jsonObject["spotlights"]?.jsonArray.toString())
    }
}