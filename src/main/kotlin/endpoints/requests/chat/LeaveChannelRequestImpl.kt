package endpoints.requests.chat

import endpoints.requests.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class LeaveChannelRequestImpl(val channelId: Int, val userId: Int) : EndpointRequest<Unit> {

    override fun endpoint(): String = "chat/channels/$channelId/users/$userId"

    override suspend fun request(client: HttpClient) {
        client.delete(this.url)
    }
}