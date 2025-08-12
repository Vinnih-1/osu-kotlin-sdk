package endpoints.beatmap_packs

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.BeatmapPackResponse
import enums.BeatmapPackType

class GetBeatmapPacksRequestImpl(val type: BeatmapPackType?, val cursor: String?) : EndpointRequest<BeatmapPackResponse> {

    override fun endpoint(): String = "beatmaps/packs"

    override suspend fun request(client: HttpClient): BeatmapPackResponse {
        val response = client.get(this.url) {
            parameter("type", type.toString().lowercase())
            parameter("cursor_string", cursor)
        }
        return json.decodeFromString<BeatmapPackResponse>(response.bodyAsText())
    }
}