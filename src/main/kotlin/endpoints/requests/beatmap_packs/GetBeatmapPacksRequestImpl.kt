package endpoints.requests.beatmap_packs

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmap_pack.BeatmapPackResponse
import enums.BeatmapPackType
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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