package endpoints.user

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.Score

class GetUserScoresRequestImpl(
    val userId: Int,
    val type: Score.ScoreType?,
    val legacyOnly: Boolean?,
    val includeFails: Boolean?,
    val offset: Int?,
    val limit: Int?
) : EndpointRequest<List<Score>> {

    override fun endpoint(): String = "users/${userId}/scores/${type.toString().lowercase()}"

    override suspend fun request(client: HttpClient): List<Score> {
        val response = client.get(this.url) {
            parameter("legacy_only", if (legacyOnly == true) 1 else 0)
            parameter("include_fails", if (includeFails == true) 1 else 0)
            parameter("offset", offset)
            parameter("limit", limit)
        }
        return json.decodeFromString<List<Score>>(response.bodyAsText())
    }
}