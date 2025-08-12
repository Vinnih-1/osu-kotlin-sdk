package events.impl

import BeatmapEvent
import enums.ModeEnum
import UserEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("rank")
data class RankEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.RANK,
    val scoreRank: String? = null,
    val rank: Int,
    val mode: ModeEnum,
    val beatmapEvent: BeatmapEvent,
    val userEvent: UserEvent
) : Event()