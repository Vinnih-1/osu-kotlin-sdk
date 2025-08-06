package models

import ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.ScoreLegacy.CurrentUserAttributes

@Serializable
data class Score(
    val id: Long,
    val accuracy: Float,
    val bestId: Int?,
    val maxCombo: Int,
    val beatmapId: Int,
    val buildId: Int?,
    val classicTotalScore: Int,
    val endedAt: String,
    val hasReplay: Boolean,
    val isPerfectCombo: Boolean,
    val legacyPerfect: Boolean,
    val legacyScoreId: Long?,
    val legacyTotalScore: Long,
    val mods: List<Mod>,
    val passed: Boolean,
    val pp: Float?,
    val preserve: Boolean,
    val processed: Boolean,
    val rank: String,
    val ranked: Boolean,
    val rulesetId: Int,
    val startedAt: String?,
    val statistics: Statistics,
    val totalScore: Int,
    val type: String,
    val userId: Int,

    /**
     * Optional attributes
     *
     * Following are attributes which may be additionally included in the response.
     * Relevant endpoints should list them if applicable.
     */
    val beatmap: Beatmap? = null,
    val beatmapset: Beatmapset? = null,
    val currentUserAttributes: CurrentUserAttributes? = null,
    val match: String? = null, //Undocumented attribute
    val position: Int? = null,
    val rankCountry: Int? = null,
    val rankGlobal: Int? = null,
    val user: UserCompact? = null,
    val weight: Weight? = null,

    // Only for multiplayer score
    val playlistItemId: Int? = null,
    val roomId: Int? = null,
) {

    enum class ScoreType {
        BEST, FIRSTS, RECENT
    }

    @Serializable
    data class Mod(
        val acronym: String,
        val settings: Settings? = null
    ) {

        @Serializable
        data class Settings (
            val speedChange: Float? = null,
            val adjustPitch: Boolean? = null,
            val restart: Boolean? = null,
            val accuracyJudgeMode: String? = null,
            val circleSize: Float? = null,
            val approachRate: Float? = null,
            val drainRate: Float? = null,
            val overallDifficulty: Float? = null,

        )
    }

    @Serializable
    data class Statistics(
        val miss: Int? = null,
        val meh: Int? = null,
        val ok: Int? = null,
        val good: Int? = null,
        val great: Int? = null,
        val perfect: Int? = null,
        val smallTickMiss: Int? = null,
        val smallTickHit: Int? = null,
        val largeTickMiss: Int? = null,
        val largeTickHit: Int? = null,
        val smallBonus: Int? = null,
        val largeBonus: Int? = null,
        val ignoreMiss: Int? = null,
        val ignoreHit: Int? = null,
        val comboBreak: Int? = null,
        val sliderTailHit: Int? = null,
        val legacyComboIncrease: Int? = null
    )

    @Serializable
    data class Weight(
        val percentage: Float,
        val pp: Float
    )
}

@Serializable
data class ScoreLegacy(
    val id: String,
    val accuracy: Float,
    val bestId: String?,
    val maxCombo: Int,
    val mods: List<Mod>,
    val passed: Boolean,
    val perfect: Boolean,
    val score: Int,
    val statistics: Statistics,
    val pp: Float,
    val rank: String,
    val createdAt: String,
    val mode: ModeEnum,
    val modeInt: Int,
    val replay: Boolean,

    /**
     * Optional attributes
     *
     * Following are attributes which may be additionally included in the response.
     * Relevant endpoints should list them if applicable.
     */

    val beatmap: Beatmap? = null,
    val beatmapset: Beatmapset? = null,
    val currentUserAttributes: CurrentUserAttributes? = null,
    val match: String? = null, //Undocumented attribute
    val position: Int? = null,
    val rankCountry: Int? = null,
    val rankGlobal: Int? = null,
    val user: UserCompact? = null,
    val type: String? = null,
    val weight: Score.Weight? = null
) {

    @Serializable
    data class CurrentUserAttributes(
        val pin: Pin? = null
    ) {

        @Serializable
        data class Pin(
            val isPinned: Boolean,
            val scoreId: Long
        )
    }

    @Serializable
    data class Statistics(
        @SerialName("count_50") val count50: Int,
        @SerialName("count_100") val count100: Int,
        @SerialName("count_300") val count300: Int,

        val countGeki: Int? = null,
        val countKatu: Int? = null,
        val countMiss: Int? = null,
    )

    @Serializable
    enum class Mod {
        @SerialName("NF") NO_FAIL,
        @SerialName("EZ") EASY,
        @SerialName("HD") HIDDEN,
        @SerialName("HR") HARD_ROCK,
        @SerialName("SD") SUDDEN_DEATH,
        @SerialName("DT") DOUBLE_TIME,
        @SerialName("NC") NIGHTCORE,
        @SerialName("HT") HALF_TIME,
        @SerialName("FL") FLASHLIGHT,
        @SerialName("SO") SPUN_OUT,
        @SerialName("PF") PERFECT,
        @SerialName("RX") RELAX,
        @SerialName("AP") AUTOPILOT,
        @SerialName("SV2") SCORE_V2
    }
}