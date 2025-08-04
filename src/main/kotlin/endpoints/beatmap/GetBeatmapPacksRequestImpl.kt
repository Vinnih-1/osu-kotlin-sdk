package endpoints.beatmap

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.BeatmapPack
import models.BeatmapPackResponse

class GetBeatmapPacksRequestImpl(val type: BeatmapPack.Type?, val cursor: String?) : EndpointRequest<BeatmapPackResponse> {

    override fun endpoint(): String = "beatmaps/packs"

    override suspend fun request(client: HttpClient): BeatmapPackResponse {
        val response = client.get(this.url) {
            parameter("type", type.toString().lowercase())
            parameter("cursor_string", cursor)
        }
        return json.decodeFromString<BeatmapPackResponse>(response.bodyAsText())
    }
}