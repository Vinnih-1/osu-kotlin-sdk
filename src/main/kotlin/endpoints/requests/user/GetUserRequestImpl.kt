package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.Ruleset
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.users.User

class GetUserRequestImpl(val user: String, val mode: Ruleset) : EndpointRequest<User> {

    override fun endpoint(): String = "users/${user}/${mode.ruleset}"

    override suspend fun request(client: HttpClient): User {
        val response = client.get(this.url)
        return json.decodeFromString<User>(response.bodyAsText())
    }
}