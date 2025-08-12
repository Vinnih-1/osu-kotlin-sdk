package events.impl

import UserEvent
import events.BeatmapsetEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapsetUpdate")
data class BeatmapsetUpdateEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAPSET_UPDATE,
    val beatmapsetEvent: BeatmapsetEvent,
    val userEvent: UserEvent
) : Event()