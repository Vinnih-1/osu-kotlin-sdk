package endpoints.requests.ranking

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.rankings.SpotlightsRankingResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetSpotlightsRequestImpl : EndpointRequest<SpotlightsRankingResponse> {

    override fun endpoint(): String = "spotlights"

    override suspend fun request(client: HttpClient): SpotlightsRankingResponse {
        val response = client.get(this.url)
        return json.decodeFromString<SpotlightsRankingResponse>(response.bodyAsText())
    }
}