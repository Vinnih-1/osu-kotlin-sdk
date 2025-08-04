package events.impl

import User
import events.Beatmapset
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapsetUpload")
data class BeatmapsetUploadEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAPSET_UPLOAD,
    val beatmapset: Beatmapset,
    val user: User
) : Event() {
}