package models

import ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchEvent(
    val id: Long,
    val detail: Detail,
    val timestamp: String,
    val userId: Int? = null,

    /**
     * Optional Attributes
     */
    val matchGame: MatchGame? = null
) {

    @Serializable
    data class Detail(
        val type: MatchEventType,
        val text: String? = null
    )

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

    @Serializable
    data class MatchGame(
        val id: Int,
        val beatmap: Beatmap,
        val beatmapId: Int,
        val startTime: String,
        val endTime: String? = null,
        val mode: ModeEnum,
        val modeInt: Int,
        val mods: List<Score.Mod>,
        val scores: List<Score>,
        val scoringType: String,
        val teamType: String
    )
}