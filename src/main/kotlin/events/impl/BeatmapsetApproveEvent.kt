package events.impl

import UserEventObject
import events.BeatmapsetEventObject
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
    val beatmapset: BeatmapsetEventObject,
    val user: UserEventObject
) : Event()