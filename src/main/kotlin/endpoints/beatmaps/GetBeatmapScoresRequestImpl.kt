package endpoints.beatmaps

import OsuKDK.Companion.json
import enums.ModeEnum
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.beatmaps.BeatmapScores

class GetBeatmapScoresRequestImpl(
    val beatmapId: Int,
    val legacyOnly: Boolean?,
    val mode: ModeEnum?,
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