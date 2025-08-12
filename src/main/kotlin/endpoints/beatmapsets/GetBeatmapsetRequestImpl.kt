package endpoints.beatmapsets

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.Beatmapset

class GetBeatmapsetRequestImpl(val beatmapsetId: Int) : EndpointRequest<Beatmapset> {

    override fun endpoint(): String = "beatmapsets/${beatmapsetId}"

    override suspend fun request(client: HttpClient): Beatmapset {
        val response = client.get(this.url)
        return json.decodeFromString<Beatmapset>(response.bodyAsText())
    }
}