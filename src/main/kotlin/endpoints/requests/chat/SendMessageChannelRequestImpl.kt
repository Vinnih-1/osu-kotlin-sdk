package endpoints.requests.chat

import OsuKDK
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import models.chat.ChatMessage

class SendMessageChannelRequestImpl(
    val channelId: Int,
    val message: String,
    val isAction: Boolean
) : EndpointRequest<ChatMessage> {

    override fun endpoint(): String = "chat/channels/$channelId/messages"

    override suspend fun request(client: HttpClient): ChatMessage {
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(buildJsonObject {
                put("message", JsonPrimitive(message))
                put("is_action", JsonPrimitive(isAction))
            }.toString())
        }
        return OsuKDK.json.decodeFromString<ChatMessage>(response.bodyAsText())
    }
}