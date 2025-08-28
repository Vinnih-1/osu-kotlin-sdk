package models.chat

import kotlinx.serialization.Serializable
import models.users.UserCompact

@Serializable
data class ChatMessage(
    val channelId: Int,
    val content: String,
    val isAction: Boolean,
    val messageId: Long,
    val senderId: Int,
    val timestamp: String,
    val type: String,
    val uuid: String? = null,
    val sender: UserCompact? = null
)