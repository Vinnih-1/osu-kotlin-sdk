package events.impl

import User
import events.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("userSupportGift")
data class UserSupportGiftEvent(
    override val id: Int? = null,
    override val createdAt: String? = null,

    val eventType: EventType = EventType.USER_SUPPORT_GIFT,
    val user: User
) : Event() {
}