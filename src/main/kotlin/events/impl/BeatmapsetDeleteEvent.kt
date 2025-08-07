package events.impl

import events.Beatmapset
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapsetDelete")
data class BeatmapsetDeleteEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAPSET_DELETE,
    val beatmapset: Beatmapset
) : Event() {
}