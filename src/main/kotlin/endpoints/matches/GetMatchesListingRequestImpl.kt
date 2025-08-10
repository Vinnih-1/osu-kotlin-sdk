package endpoints.matches

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.Match

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

@Serializable
data class MatchesResponse(
    val matches: List<Match>,
    val cursorString: String? = null,
    val params: Params,
) {

    @Serializable
    data class Params(
        val limit: Int,
        val sort: String
    )
}