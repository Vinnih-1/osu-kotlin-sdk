package endpoints.requests.chat

import endpoints.requests.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.chat.ChatMessage

class GetChannelMessagesRequestImpl(
    val channelId: Int,
    val limit: Int?,
    val since: Int?,
    val until: Int?
) : EndpointRequest<List<ChatMessage>> {

    override fun endpoint(): String = "chat/channels/$channelId/messages"

    override suspend fun request(client: HttpClient): List<ChatMessage> {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("since", since)
            parameter("until", until)
        }
        return OsuKDK.json.decodeFromString<List<ChatMessage>>(response.bodyAsText())
    }
}