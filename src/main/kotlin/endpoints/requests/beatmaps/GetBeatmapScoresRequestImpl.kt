package endpoints.requests.beatmaps

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.Ruleset
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.BeatmapScores

class GetBeatmapScoresRequestImpl(
    val beatmapId: Int,
    val legacyOnly: Boolean?,
    val mode: Ruleset?,
    val mods: String?,
    val type: String?
) : EndpointRequest<BeatmapScores> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/scores"

    override suspend fun request(client: HttpClient): BeatmapScores {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
            parameter("mode", mode?.ruleset)
            // TODO: parameter("mods", mods)
            // TODO: parameter("type", type)
        }
        return json.decodeFromString<BeatmapScores>(response.bodyAsText())
    }
}