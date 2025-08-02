package endpoints.user

import OsuKDK.Companion.json
import ModeEnum
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import models.User

class GetOwnDataRequestImpl(val modeEnum: ModeEnum?) : EndpointRequest<User> {

    override fun endpoint(): String = "me/${modeEnum?.name?.lowercase()}"

    override suspend fun request(client: HttpClient): User {
        val response = client.get(this.url)
        return json.decodeFromString<User>(response.bodyAsText())
    }
}