package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.forums.ForumTopicAndPostsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetTopicAndPostRequestImpl(
    val topicId: Int,
    val sort: String?,
    val limit: Int?,
    val start: String?,
    val end: String?,
    val cursorString: String?
) : EndpointRequest<ForumTopicAndPostsResponse> {
    override fun endpoint(): String = "forums/topics/${topicId}"

    override suspend fun request(client: HttpClient): ForumTopicAndPostsResponse {
        val response = client.get(this.url) {
            parameter("sort", sort)
            parameter("limit", limit)
            parameter("start", start)
            parameter("end", end)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<ForumTopicAndPostsResponse>(response.bodyAsText())
    }
}