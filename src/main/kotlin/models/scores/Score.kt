package models.scores

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap
import models.beatmaps.Beatmapset
import models.scores.ScoreLegacy.CurrentUserAttributes
import models.users.UserCompact

@Serializable
data class Score(
    val id: Long,
    val accuracy: Float,
    val bestId: Long?,
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