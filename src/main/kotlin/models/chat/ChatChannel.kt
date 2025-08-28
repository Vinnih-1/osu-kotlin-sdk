package models.chat

import enums.ChannelType
import kotlinx.serialization.Serializable

@Serializable
data class ChatChannel(
    val channelId: Int,
    val name: String,
    val description: String? = null,
    val icon: String? = null,
    val type: ChannelType,
    val messageLengthLimit: Int,
    val moderated: Boolean,
    val uuid: String? = null
)