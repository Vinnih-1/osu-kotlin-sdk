package endpoints.requests.beatmaps

import OsuKDK.Companion.json
import enums.ModeEnum
import endpoints.requests.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.beatmaps.BeatmapUserScore

class GetUserBeatmapScoreRequestImpl(
    val beatmapId: Int,
    val userId: Int,
    val legacyOnly: Boolean?,
    val mode: ModeEnum?,
    val mods: String?
) : EndpointRequest<BeatmapUserScore> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/scores/users/${userId}"

    override suspend fun request(client: HttpClient): BeatmapUserScore {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
            parameter("mode", mode?.ruleset)
            // TODO: parameter("mods", mods)
        }
        return json.decodeFromString<BeatmapUserScore>(response.bodyAsText())
    }
}