package events.impl

import BeatmapEventObject
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("beatmapPlaycount")
data class BeatmapPlaycountEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.BEATMAP_PLAYCOUNT,
    val beatmap: BeatmapEventObject,
    val count: Int
) : Event()