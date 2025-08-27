package events.impl

import BeatmapEventObject
import enums.Ruleset
import UserEventObject
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("rankLost")
data class RankLostEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.RANK_LOST,
    val mode: Ruleset,
    val beatmap: BeatmapEventObject,
    val user: UserEventObject
) : Event()