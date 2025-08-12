package events.impl

import UserEvent
import events.BeatmapsetEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapsetApprove")
data class BeatmapsetApproveEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAPSET_APPROVE,
    val approval: String,
    val beatmapsetEvent: BeatmapsetEvent,
    val userEvent: UserEvent
) : Event()