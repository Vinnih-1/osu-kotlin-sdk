package models

import ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beatmap (
    val accuracy: Float,
    val ar: Float,
    val beatmapsetId: Int,
    val bpm: Float,
    val convert: Boolean,
    val countCircles: Int,
    val countSliders: Int,
    val countSpinners: Int,
    val cs: Float,
    val deletedAt: String?,
    val drain: Float,
    val hitLength: Int,
    val isScoreable: Boolean,
    val lastUpdated: String,
    val modeInt: Int,
    val passcount: Int,
    val playcount: Int,
    val ranked: Int,
    val url: String
) {

    @Serializable
    data class BeatmapCompact(
        val beatmapsetId: Int,
        val difficultyRating: Float,
        val id: Int,
        val mode: ModeEnum,
        val status: RankStatus,
        val totalLength: Int,
        val userId: Int,
        val version: String,

        /**
         * Optional attributes
         *
         * Following are attributes which may be additionally included in the response.
         * Relevant endpoints should list them if applicable.
         */

        val beatmapset: Beatmapset? = null,
        val checksum: String? = null,
        val currentUserPlaycount: Int? = null,
        val failtimes: Failtimes? = null,
        val maxCombo: Int? = null,
        val beatmapOwner: List<BeatmapOwner>? = null
    )

    @Serializable
    enum class RankStatus(val code: Int) {
        @SerialName("graveyard") GRAVEYARD(-2),
        @SerialName("wip") WIP(-1),
        @SerialName("pending") PENDING(0),
        @SerialName("ranked") RANKED(1),
        @SerialName("approved") APPROVED(2),
        @SerialName("qualified") QUALIFIED(3),
        @SerialName("loved") LOVED(4)
    }

    @Serializable
    data class Failtimes(
        val exit: List<Int>? = null,
        val fail: List<Int>? = null,
    )

    @Serializable
    data class BeatmapOwner(
        val id: Int,
        val username: String
    )
}