package endpoints.beatmaps

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.Beatmap

class GetBeatmapRequestImpl(val beatmapId: Int) : EndpointRequest<Beatmap> {

    override fun endpoint(): String = "beatmaps/${beatmapId}"

    override suspend fun request(client: HttpClient): Beatmap {
        val response = client.get(this.url)
        return json.decodeFromString(response.bodyAsText())
    }
}