package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.forums.ForumTopic

class GetTopicListingRequestImpl(
    val forumId: String?,
    val sort: String?,
    val limit: Int?,
    val cursorString: String?
) : EndpointRequest<ForumTopicResponse> {

    override fun endpoint(): String = "forums/topics"

    override suspend fun request(client: HttpClient): ForumTopicResponse {
        val response = client.get(this.url) {
            parameter("forum_id", forumId)
            parameter("sort", sort)
            parameter("limit", limit)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<ForumTopicResponse>(response.bodyAsText())
    }

}

@Serializable
data class ForumTopicResponse(
    val topics: List<ForumTopic>,
    val cursorString: String
)