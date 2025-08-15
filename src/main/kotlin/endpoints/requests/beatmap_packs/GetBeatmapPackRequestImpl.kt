package endpoints.requests.beatmap_packs

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.beatmaps.BeatmapPack

class GetBeatmapPackRequestImpl(val pack: String, val legacyOnly: Boolean?) : EndpointRequest<BeatmapPack> {

    override fun endpoint(): String = "beatmaps/packs/${pack}"

    override suspend fun request(client: HttpClient): BeatmapPack {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
        }
        return json.decodeFromString<BeatmapPack>(response.bodyAsText())
    }
}