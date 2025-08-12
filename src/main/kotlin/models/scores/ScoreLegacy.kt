package models.scores

import enums.ModLegacy
import enums.ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap
import models.beatmaps.Beatmapset
import models.users.UserCompact

@Serializable
data class ScoreLegacy(
    val id: String,
    val accuracy: Float,
    val bestId: String?,
    val maxCombo: Int,
    val mods: List<ModLegacy>,
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
}