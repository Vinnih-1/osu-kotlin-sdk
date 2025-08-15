package events.impl

import UserEventObject
import events.AchievementEventObject
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("achievement")
data class AchievementEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.ACHIEVEMENT,
    val achievement: AchievementEventObject,
    val user: UserEventObject
) : Event()