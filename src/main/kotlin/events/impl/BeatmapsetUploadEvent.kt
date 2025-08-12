package events.impl

import UserEventObject
import events.BeatmapsetEventObject
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapsetUpload")
data class BeatmapsetUploadEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAPSET_UPLOAD,
    val beatmapset: BeatmapsetEventObject,
    val user: UserEventObject
) : Event()