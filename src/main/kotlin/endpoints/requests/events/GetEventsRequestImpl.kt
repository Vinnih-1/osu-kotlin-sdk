package endpoints.requests.events

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.events.EventsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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