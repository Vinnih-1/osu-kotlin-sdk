package endpoints.news

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.news.NewsPost

class GetNewsPostRequestImpl(
    val slug: String,
    val key: String?
) : EndpointRequest<NewsPost> {

    override fun endpoint(): String = "news/${slug}"

    override suspend fun request(client: HttpClient): NewsPost {
        val response = client.get(this.url) {
            parameter("key", key)
        }
        return json.decodeFromString<NewsPost>(response.bodyAsText())
    }
}