package models.matches

import enums.MatchEventType
import enums.ModeEnum
import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap
import models.scores.Mod
import models.scores.Score

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
    data class MatchGame(
        val id: Int,
        val beatmap: Beatmap,
        val beatmapId: Int,
        val startTime: String,
        val endTime: String? = null,
        val mode: ModeEnum,
        val modeInt: Int,
        val mods: List<Mod>,
        val scores: List<Score>,
        val scoringType: String,
        val teamType: String
    )
}