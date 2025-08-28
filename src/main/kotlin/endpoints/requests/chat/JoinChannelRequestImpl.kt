package endpoints.requests.chat

import OsuKDK
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.chat.ChatChannel

class JoinChannelRequestImpl(val channelId: Int, val userId: Int) : EndpointRequest<ChatChannel> {

    override fun endpoint(): String = "chat/channels/$channelId/users/$userId"

    override suspend fun request(client: HttpClient): ChatChannel {
        val response = client.put(this.url)
        return OsuKDK.json.decodeFromString<ChatChannel>(response.bodyAsText())
    }
}