package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MatchEventType {
    @SerialName("host-changed")
    HOST_CHANGED,

    @SerialName("match-created")
    MATCH_CREATED,

    @SerialName("match-disbanded")
    MATCH_DISBANDED,

    @SerialName("other")
    OTHER,

    @SerialName("player-joined")
    PLAYER_JOINED,

    @SerialName("player-kicked")
    PLAYER_KICKED,

    @SerialName("player-left")
    PLAYER_LEFT
}