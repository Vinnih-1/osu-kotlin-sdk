package endpoints.oauth_tokens

import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*

class RevokeTokenRequestImpl : EndpointRequest<Unit> {

    override fun endpoint(): String = "oauth/tokens/current"

    override suspend fun request(client: HttpClient): Unit {
        client.delete(this.url)
    }
}