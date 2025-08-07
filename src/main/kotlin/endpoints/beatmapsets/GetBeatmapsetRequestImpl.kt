package endpoints.beatmapsets

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import models.Beatmapset

class GetBeatmapsetRequestImpl(val beatmapsetId: Int) : EndpointRequest<Beatmapset> {

    override fun endpoint(): String = "beatmapsets/${beatmapsetId}"

    override suspend fun request(client: HttpClient): Beatmapset {
        val response = client.get(this.url)
        return json.decodeFromString<Beatmapset>(response.bodyAsText())
    }
}