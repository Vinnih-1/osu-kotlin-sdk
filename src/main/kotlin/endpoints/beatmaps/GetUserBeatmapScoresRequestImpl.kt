package endpoints.beatmaps

import enums.ModeEnum
import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.scores.Score

class GetUserBeatmapScoresRequestImpl(
    val beatmapId: Int,
    val userId: Int,
    val legacyOnly: Boolean?,
    val mode: ModeEnum?
) : EndpointRequest<List<Score>> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/scores/users/${userId}/all"

    override suspend fun request(client: HttpClient): List<Score> {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
            parameter("mode", mode?.ruleset)
        }
        val responseJson = json.parseToJsonElement(response.bodyAsText()).jsonObject
        return json.decodeFromString<List<Score>>(responseJson["scores"]?.jsonArray.toString())
    }
}