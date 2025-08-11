package endpoints.news

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.NewsPost

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

@Serializable
data class NewsListingResponse(
    @SerialName("news_posts")
    val newsPosts: List<NewsPost>,
    @SerialName("news_sidebar")
    val newsSidebar: NewsSidebar,
    val search: SearchParams,
    @SerialName("cursor_string")
    val cursorString: String? = null
) {
    @Serializable
    data class NewsSidebar(
        @SerialName("current_year")
        val currentYear: Int,
        @SerialName("news_posts")
        val newsPosts: List<NewsPost>,
        val years: List<Int>
    )

    @Serializable
    data class SearchParams(
        val limit: Int,
        val sort: String
    )
}