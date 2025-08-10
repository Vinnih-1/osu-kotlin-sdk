package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.ForumPost
import models.ForumTopic

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

@Serializable
data class ForumTopicAndPostsResponse (
    val posts: List<ForumPost>,
    val topic: ForumTopic,
    val cursorString: String? = null
)