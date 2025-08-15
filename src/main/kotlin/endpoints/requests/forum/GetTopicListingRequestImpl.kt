package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.forums.ForumTopicResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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