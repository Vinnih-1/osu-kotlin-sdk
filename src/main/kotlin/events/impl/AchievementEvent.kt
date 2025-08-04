package events.impl

import User
import events.Achievement
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("achievement")
data class AchievementEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.ACHIEVEMENT,
    val achievement: Achievement,
    val user: User
) : Event() {
}