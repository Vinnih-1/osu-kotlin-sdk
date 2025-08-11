package endpoints.oauth_tokens

import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class RevokeTokenRequestImpl : EndpointRequest<Unit> {

    override fun endpoint(): String = "oauth/tokens/current"

    override suspend fun request(client: HttpClient): Unit {
        client.delete(this.url)
    }
}