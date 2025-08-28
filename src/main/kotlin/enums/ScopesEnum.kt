package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ScopesEnum(val value: String) {
    @SerialName("chat.read") CHAT_READ("chat.read"),
    @SerialName("chat.write") CHAT_WRITE("chat.write"),
    @SerialName("chat.write_manage") CHAT_WRITE_MANAGE("chat.write_manage"),
    @SerialName("delegate") DELEGATE("delegate"),
    @SerialName("forum.write") FORUM_WRITE("forum.write"),
    @SerialName("friends.read") FRIENDS_READ("friends.read"),
    @SerialName("identify") IDENTIFY("identify"),
    @SerialName("public") PUBLIC("public"),
}