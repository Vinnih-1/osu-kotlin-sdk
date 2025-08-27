package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.forums.CreateTopicResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

class CreateTopicRequestImpl(
    val body: String,
    val forumId: Int,
    val title: String,
    val withPoll: Boolean?,
    val pollHideResults: Boolean?,
    val pollLengthDays: Int?,
    val pollMaxOptions: Int?,
    val pollOptions: String?,
    val pollTitle: String?,
    val pollVoteChange: Boolean?
) : EndpointRequest<CreateTopicResponse> {

    override fun endpoint(): String = "forums/topics"

    override suspend fun request(client: HttpClient): CreateTopicResponse {
        val bodyJson = buildJsonObject {
            put("body", JsonPrimitive(body))
            put("forum_id", JsonPrimitive(forumId.toString()))
            put("title", JsonPrimitive(title))
            put("with_poll", JsonPrimitive(withPoll.toString()))
            put("forum_topic_poll[hide_results]", JsonPrimitive(pollHideResults.toString()))
            put("forum_topic_poll[length_days]", JsonPrimitive(pollLengthDays.toString()))
            put("forum_topic_poll[max_options]", JsonPrimitive(pollMaxOptions.toString()))
            put("forum_topic_poll[options]", JsonPrimitive(pollOptions.toString()))
            put("forum_topic_poll[title]", JsonPrimitive(pollTitle.toString()))
            put("forum_topic_poll[vote_change]", JsonPrimitive(pollVoteChange.toString()))
        }
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(bodyJson.toString())
        }

        return json.decodeFromString<CreateTopicResponse>(response.bodyAsText())
    }
}