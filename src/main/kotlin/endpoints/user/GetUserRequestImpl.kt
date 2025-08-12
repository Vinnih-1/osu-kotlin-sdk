package endpoints.user

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import enums.ModeEnum
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.users.User

class GetUserRequestsImpl(val userId: Int, val mode: ModeEnum) : EndpointRequest<User> {

    override fun endpoint(): String = "users/${userId}/${mode.ruleset}"

    override suspend fun request(client: HttpClient): User {
        val response = client.get(this.url)
        return json.decodeFromString<User>(response.bodyAsText())
    }
}