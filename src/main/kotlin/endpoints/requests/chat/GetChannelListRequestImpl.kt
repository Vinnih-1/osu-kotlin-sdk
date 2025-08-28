package endpoints.requests.chat

import endpoints.requests.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import models.chat.ChatChannel

class GetChannelListRequestImpl : EndpointRequest<List<ChatChannel>> {

    override fun endpoint(): String = "chat/channels"

    override suspend fun request(client: HttpClient): List<ChatChannel> {
        val response = client.get(this.url)
        return OsuKDK.json.decodeFromString<List<ChatChannel>>(response.bodyAsText())
    }
}