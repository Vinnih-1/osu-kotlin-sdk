package events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EventType {
    @SerialName("achievement")
    ACHIEVEMENT,

    @SerialName("beatmapPlaycount")
    BEATMAP_PLAYCOUNT,

    @SerialName("beatmapsetApprove")
    BEATMAPSET_APPROVE,

    @SerialName("beatmapsetDelete")
    BEATMAPSET_DELETE,

    @SerialName("beatmapsetRevive")
    BEATMAPSET_REVIVE,

    @SerialName("beatmapsetUpdate")
    BEATMAPSET_UPDATE,

    @SerialName("beatmapsetUpload")
    BEATMAPSET_UPLOAD,

    @SerialName("rank")
    RANK,

    @SerialName("rankLost")
    RANK_LOST,

    @SerialName("userSupportAgain")
    USER_SUPPORT_AGAIN,

    @SerialName("userSupportFirst")
    USER_SUPPORT_FIRST,

    @SerialName("userSupportGift")
    USER_SUPPORT_GIFT,

    @SerialName("usernameChange")
    USERNAME_CHANGE
}