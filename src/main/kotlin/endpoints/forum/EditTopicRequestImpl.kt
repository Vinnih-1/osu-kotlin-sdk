package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.ForumTopic

class EditTopicRequestImpl(
    val topicId: Int,
    val title: String
) : EndpointRequest<ForumTopic> {

    override fun endpoint(): String = "forums/topics/${topicId}"

    override suspend fun request(client: HttpClient): ForumTopic {
        val response = client.put(this.url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(mapOf(
                "forum_topic[topic_title]" to title
            )))
        }
        return json.decodeFromString<ForumTopic>(response.bodyAsText())
    }
}