package events.impl

import User
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("userSupportFirst")
data class UserSupportFirstEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.USER_SUPPORT_FIRST,
    val user: User
) : Event() {
}