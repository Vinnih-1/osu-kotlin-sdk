package endpoints.beatmaps

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.Beatmap

class GetBeatmapsRequestImpl(val ids: List<Int>?) : EndpointRequest<List<Beatmap>> {

    override fun endpoint(): String = "beatmaps"

    override suspend fun request(client: HttpClient): List<Beatmap> {
        val response = client.get(this.url) {
            ids?.forEach { parameter("ids[]", it) }
        }
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject
        return json.decodeFromString<List<Beatmap>>(jsonObject["beatmaps"]?.jsonArray.toString())
    }
}