package events.impl

import UserEvent
import enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("userSupportAgain")
data class UserSupportAgainEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.USER_SUPPORT_AGAIN,
    val userEvent: UserEvent
) : Event()