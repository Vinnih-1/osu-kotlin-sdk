package events.impl

import BeatmapEvent
import enums.ModeEnum
import UserEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("rankLost")
data class RankLostEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.RANK_LOST,
    val mode: ModeEnum,
    val beatmapEvent: BeatmapEvent,
    val userEvent: UserEvent
) : Event()