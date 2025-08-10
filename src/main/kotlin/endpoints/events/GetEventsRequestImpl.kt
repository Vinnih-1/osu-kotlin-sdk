package endpoints.events

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import events.impl.Event
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable

class GetEventsRequestImpl(
    val sort: String?,
    val cursorString: String?
) : EndpointRequest<EventsResponse> {

    override fun endpoint(): String = "events"

    override suspend fun request(client: HttpClient): EventsResponse {
        val response = client.get(this.url) {
            parameter("sort", sort)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<EventsResponse>(response.bodyAsText())
    }
}

@Serializable
data class EventsResponse(
    val events: List<Event>,
    val cursorString: String?
)