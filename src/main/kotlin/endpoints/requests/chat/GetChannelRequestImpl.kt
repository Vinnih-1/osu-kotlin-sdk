package endpoints.requests.chat

import endpoints.requests.EndpointRequest
import endpoints.responses.chat.GetChannelResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class GetChannelRequestImpl(val channelId: Int) : EndpointRequest<GetChannelResponse> {

    override fun endpoint(): String = "chat/channels/$channelId"

    override suspend fun request(client: HttpClient): GetChannelResponse {
        val response = client.get(this.url)
        return OsuKDK.json.decodeFromString<GetChannelResponse>(response.bodyAsText())
    }
}