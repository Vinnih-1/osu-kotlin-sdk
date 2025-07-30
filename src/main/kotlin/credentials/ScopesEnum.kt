package credentials

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ScopesEnum() {
    @SerialName("chat.read") CHAT_READ,
    @SerialName("chat.write") CHAT_WRITE,
    @SerialName("chat.write_manage") CHAT_WRITE_MANAGE,
    @SerialName("delegate") DELEGATE,
    @SerialName("forum.write") FORUM_WRITE,
    @SerialName("friends.read") FRIENDS_READ,
    @SerialName("identify") IDENTIFY,
    @SerialName("public") PUBLIC
}