package events.impl

import Beatmap
import ModeEnum
import User
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("rankLost")
data class RankLostEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.RANK_LOST,
    val mode: ModeEnum,
    val beatmap: Beatmap,
    val user: User
) : Event() {
}