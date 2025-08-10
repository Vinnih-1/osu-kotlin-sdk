package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.ForumPost
import models.ForumTopic

class CreateTopicRequestImpl(val topicRequest: TopicRequest) : EndpointRequest<CreateTopicResponse> {

    override fun endpoint(): String = "forums/topics"

    override suspend fun request(client: HttpClient): CreateTopicResponse {
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(topicRequest))
        }
        println(response.bodyAsText())
        return json.decodeFromString<CreateTopicResponse>(response.bodyAsText())
    }
}

@Serializable
data class TopicRequest(
    val body: String,
    val forumId: Int,
    val title: String,
    val withPoll: Boolean? = false,

    @SerialName("forum_topic_poll[hide_results]")
    val pollHideResults: Boolean? = null,

    @SerialName("forum_topic_poll[length_days]")
    val pollLengthDays: Int? = null,

    @SerialName("forum_topic_poll[max_options]")
    val pollMaxOptions: Int? = null,

    @SerialName("forum_topic_poll[options]")
    val pollOptions: String? = null,

    @SerialName("forum_topic_poll[title]")
    val pollTitle: String? = null,

    @SerialName("forum_topic_poll[vote_change]")
    val pollVoteChange: Boolean? = null
)

@Serializable
data class CreateTopicResponse(
    val topic: ForumTopic,
    val post: ForumPost
)