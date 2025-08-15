package endpoints.requests.ranking

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.rankings.KudosuRankingResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetKudosuRankingRequestImpl(val page: Int?) : EndpointRequest<KudosuRankingResponse> {

    override fun endpoint(): String = "rankings/kudosu"

    override suspend fun request(client: HttpClient): KudosuRankingResponse {
        val response = client.get(this.url) {
            parameter("page", page)
        }
        return json.decodeFromString<KudosuRankingResponse>(response.bodyAsText())
    }
}