package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.forums.ForumPost

class ReplyTopicRequestImpl(
    val topicId: Int,
    val bodyString: String
) : EndpointRequest<ForumPost> {
    override fun endpoint(): String = "forums/topics/${topicId}/reply"

    override suspend fun request(client: HttpClient): ForumPost {
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(mapOf(
                "body" to bodyString
            )))
        }
        return json.decodeFromString<ForumPost>(response.bodyAsText())
    }
}