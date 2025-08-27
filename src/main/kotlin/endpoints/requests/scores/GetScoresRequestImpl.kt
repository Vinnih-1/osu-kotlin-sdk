package endpoints.requests.scores

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.scores.ScoreResponse
import enums.Ruleset
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetScoresRequestImpl(val mode: Ruleset?, val cursorString: String?) : EndpointRequest<ScoreResponse> {

    override fun endpoint(): String = "scores"

    override suspend fun request(client: HttpClient): ScoreResponse {
        val response = client.get(this.url) {
            parameter("ruleset", mode?.toString()?.lowercase())
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<ScoreResponse>(response.bodyAsText())
    }
}