package events.impl

import UserEvent
import events.AchievementEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("achievement")
data class AchievementEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.ACHIEVEMENT,
    val achievementEvent: AchievementEvent,
    val userEvent: UserEvent
) : Event()