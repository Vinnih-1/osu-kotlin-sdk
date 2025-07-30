package endpoints.user

import ModeEnum
import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.User

class GetUserRequestsImpl(val userId: Int, val mode: ModeEnum) : EndpointRequest<User> {

    override fun endpoint(): String = "users/"

    override suspend fun request(client: HttpClient): User {
        val response = client.get("${this.url}${userId}/${mode.ruleset}")
        val user = json.decodeFromString<User>(response.bodyAsText())

        return user
    }
}