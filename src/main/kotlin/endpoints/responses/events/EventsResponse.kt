package endpoints.responses.events

import events.impl.Event
import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(
    val events: List<Event>,
    val cursorString: String?
)