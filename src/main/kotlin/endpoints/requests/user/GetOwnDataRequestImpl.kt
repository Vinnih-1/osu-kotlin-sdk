package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.Ruleset
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.users.User

class GetOwnDataRequestImpl(val ruleSet: Ruleset?) : EndpointRequest<User> {

    override fun endpoint(): String = "me/${ruleSet?.name?.lowercase()}"

    override suspend fun request(client: HttpClient): User {
        val response = client.get(this.url)
        return json.decodeFromString<User>(response.bodyAsText())
    }
}