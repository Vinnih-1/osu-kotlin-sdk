package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.forums.CreateTopicResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
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
        val body = buildJsonObject {
            put("body", json.parseToJsonElement(body))
            put("forum_id", json.parseToJsonElement(forumId.toString()))
            put("title", json.parseToJsonElement(title))
            put("with_poll", json.parseToJsonElement(withPoll.toString()))
            put("forum_topic_poll[hide_results]", json.parseToJsonElement(pollHideResults.toString()))
            put("forum_topic_poll[length_days]", json.parseToJsonElement(pollLengthDays.toString()))
            put("forum_topic_poll[max_options]", json.parseToJsonElement(pollMaxOptions.toString()))
            put("forum_topic_poll[options]", json.parseToJsonElement(pollOptions.toString()))
            put("forum_topic_poll[title]", json.parseToJsonElement(pollTitle.toString()))
            put("forum_topic_poll[vote_change]", json.parseToJsonElement(pollVoteChange.toString()))
        }
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(body.toString())
        }
        return json.decodeFromString<CreateTopicResponse>(response.bodyAsText())
    }
}