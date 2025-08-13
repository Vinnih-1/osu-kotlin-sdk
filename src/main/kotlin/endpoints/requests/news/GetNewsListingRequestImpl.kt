package endpoints.requests.news

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.news.NewsListingResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetNewsListingRequestImpl(
    val limit: Int?,
    val year: Int?,
    val cursorString: String?
) : EndpointRequest<NewsListingResponse> {

    override fun endpoint(): String = "news"

    override suspend fun request(client: HttpClient): NewsListingResponse {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("year", year)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<NewsListingResponse>(response.bodyAsText())
    }
}