package endpoints.requests.beatmaps

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmaps.UserBeatmapsScoresResponse
import enums.Ruleset
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetUserBeatmapScoresRequestImpl(
    val beatmapId: Int,
    val userId: Int,
    val legacyOnly: Boolean?,
    val mode: Ruleset?
) : EndpointRequest<UserBeatmapsScoresResponse> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/scores/users/${userId}/all"

    override suspend fun request(client: HttpClient): UserBeatmapsScoresResponse {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
            parameter("mode", mode?.ruleset)
        }
        return json.decodeFromString<UserBeatmapsScoresResponse>(response.bodyAsText())
    }
}