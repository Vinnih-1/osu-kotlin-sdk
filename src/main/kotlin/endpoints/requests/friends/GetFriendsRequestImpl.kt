package endpoints.requests.friends

import OsuKDK
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.users.UserCompact
import models.users.UserRelation

class GetFriendsRequestImpl : EndpointRequest<List<UserRelation>> {

    override fun endpoint(): String = "friends"

    override suspend fun request(client: HttpClient): List<UserRelation> {
        val response = client.get(this.url)
        val jsonArray = OsuKDK.json.parseToJsonElement(response.bodyAsText()).jsonArray

        if (jsonArray.isEmpty()) return listOf()
        if ("target" in jsonArray[0].jsonObject) return OsuKDK.json.decodeFromString<List<UserRelation>>(response.bodyAsText())

        return OsuKDK.json.decodeFromString<List<UserCompact>>(response.bodyAsText())
            .map { UserRelation(targetId = it.id, target = it) }
    }
}