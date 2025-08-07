package endpoints.user

import events.impl.Event.Companion.json
import endpoints.EndpointRequest
import events.impl.Event
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
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