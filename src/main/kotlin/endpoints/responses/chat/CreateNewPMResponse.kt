package endpoints.responses.chat

import kotlinx.serialization.Serializable
import models.chat.ChatChannel
import models.chat.ChatMessage

@Serializable
data class CreateNewPMResponse(
    val channel: ChatChannel,
    val message: ChatMessage,
    val newChannelId: Int
)