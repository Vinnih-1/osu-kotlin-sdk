package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.Forum
import models.ForumTopic

class GetForumAndTopicsRequestImpl(
    val forumId: Int
) : EndpointRequest<ForumAndTopicsResponse> {
    override fun endpoint(): String = "forums/${forumId}"

    override suspend fun request(client: HttpClient): ForumAndTopicsResponse {
        val response = client.get(this.url)
        return json.decodeFromString<ForumAndTopicsResponse>(response.bodyAsText())
    }
}

@Serializable
data class ForumAndTopicsResponse (
    val forum: Forum,
    val topics: List<ForumTopic>,
    val pinnedTopics: List<ForumTopic>
)