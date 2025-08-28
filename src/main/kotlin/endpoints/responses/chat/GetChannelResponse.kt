package endpoints.responses.chat

import kotlinx.serialization.Serializable
import models.chat.ChatChannel
import models.users.UserCompact

@Serializable
data class GetChannelResponse(
    val channel: ChatChannel,
    val users: List<UserCompact>
)