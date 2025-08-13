package endpoints.requests.matches

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.matches.MatchesResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetMatchesListingRequestImpl(
    val limit: Int?,
    val sort: String?,
    val cursorString: String?
) : EndpointRequest<MatchesResponse> {

    override fun endpoint(): String = "matches"

    override suspend fun request(client: HttpClient): MatchesResponse {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("sort", sort)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<MatchesResponse>(response.bodyAsText())
    }
}