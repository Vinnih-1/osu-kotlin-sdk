package endpoints.requests.user

import endpoints.requests.EndpointRequest
import events.impl.Event
import events.impl.Event.Companion.json
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Polymorphic

class GetUserRecentActivityRequestImpl(
    val userId: Int,
    val offset: Int?,
    val limit: Int?
) : EndpointRequest<List<Event>> {

    override fun endpoint(): String = "users/${userId}/recent_activity"

    override suspend fun request(client: HttpClient): List<Event> {
        val response = client.request(this.url) {
            parameter("offset", offset)
            parameter("limit", limit)
        }
        return json.decodeFromString<@Polymorphic List<Event>>(response.bodyAsText())
    }
}