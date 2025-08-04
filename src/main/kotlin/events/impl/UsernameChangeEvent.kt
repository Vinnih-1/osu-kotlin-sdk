package events.impl

import User
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("usernameChange")
data class UsernameChangeEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.USERNAME_CHANGE,
    val user: User
) : Event() {
}