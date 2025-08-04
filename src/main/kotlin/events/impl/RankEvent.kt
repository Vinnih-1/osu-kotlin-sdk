package events.impl

import Beatmap
import ModeEnum
import User
import events.EventType
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
    val beatmap: Beatmap,
    val user: User
) : Event() {


}