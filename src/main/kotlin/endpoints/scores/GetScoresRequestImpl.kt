package endpoints.scores

import OsuKDK.Companion.json
import ModeEnum
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import models.Score

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