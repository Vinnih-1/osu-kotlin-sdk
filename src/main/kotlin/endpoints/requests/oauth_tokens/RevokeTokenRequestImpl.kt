package endpoints.requests.oauth_tokens

import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*

class RevokeTokenRequestImpl : EndpointRequest<Unit> {

    override fun endpoint(): String = "oauth/tokens/current"

    override suspend fun request(client: HttpClient): Unit {
        client.delete(this.url)
    }
}