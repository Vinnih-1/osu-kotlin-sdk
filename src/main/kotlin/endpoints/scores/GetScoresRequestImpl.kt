package endpoints.scores

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import enums.ModeEnum
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.scores.Score

class GetScoresRequestImpl(val mode: ModeEnum?, val cursorString: String?) : EndpointRequest<ScoreResponse> {

    override fun endpoint(): String = "scores"

    override suspend fun request(client: HttpClient): ScoreResponse {
        val response = client.get(this.url) {
            parameter("ruleset", mode?.toString()?.lowercase())
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<ScoreResponse>(response.bodyAsText())
    }
}

@Serializable
data class ScoreResponse(
    val scores: List<Score>,
    val cursorString: String? = null
)