package endpoints.requests.matches

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.matches.MatchResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetMatchRequestImpl(
    val matchId: Long,
    val before: Int?,
    val after: Int?,
    val limit: Int?
) : EndpointRequest<MatchResponse> {

    override fun endpoint(): String = "matches/${matchId}"

    override suspend fun request(client: HttpClient): MatchResponse {
        val response = client.get(this.url) {
            parameter("before", before)
            parameter("after", after)
            parameter("limit", limit)
        }
        return json.decodeFromString<MatchResponse>(response.bodyAsText())
    }
}