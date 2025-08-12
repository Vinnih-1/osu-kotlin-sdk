package endpoints.matches

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.matches.Match
import models.matches.MatchEvent
import models.users.User

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

@Serializable
data class MatchResponse(
    val match: Match,
    val events: List<MatchEvent>,
    val users: List<User>,
    val firstEventId: Long,
    val latestEventId: Long
)