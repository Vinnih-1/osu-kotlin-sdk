package endpoints.requests.chat

import OsuKDK
import endpoints.requests.EndpointRequest
import endpoints.responses.chat.CreateNewPMResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class CreateNewPMRequestImpl(
    val targetId: Int,
    val message: String,
    val isAction: Boolean,
    val uuid: String?
) : EndpointRequest<CreateNewPMResponse> {

    override fun endpoint(): String = "chat/new"

    override suspend fun request(client: HttpClient): CreateNewPMResponse {
        val response = client.post(this.url) {
            parameter("target_id", targetId)
            parameter("message", message)
            parameter("is_action", isAction)
            parameter("uuid", uuid)
        }
        return OsuKDK.json.decodeFromString<CreateNewPMResponse>(response.bodyAsText())
    }
}