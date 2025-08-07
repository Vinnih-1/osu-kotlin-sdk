package models

import ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.Beatmap.BeatmapOwner
import models.Beatmap.Failtimes
import models.Beatmap.RankStatus

@Serializable
data class Beatmap (
    val accuracy: Float? = null,
    val ar: Float? = null,
    val bpm: Float? = null,
    val convert: Boolean? = null,
    val countCircles: Int? = null,
    val countSliders: Int? = null,
    val countSpinners: Int? = null,
    val cs: Float? = null,
    val deletedAt: String?? = null,
    val drain: Float? = null,
    val hitLength: Int? = null,
    val isScoreable: Boolean? = null,
    val lastUpdated: String? = null,
    val modeInt: Int? = null,
    val passcount: Int? = null,
    val playcount: Int? = null,
    val ranked: Int? = null,
    val url: String? = null
) : BeatmapCompact() {

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

@Serializable
open class BeatmapCompact(
    val beatmapsetId: Int? = null,
    val difficultyRating: Float? = null,
    val id: Int? = null,
    val mode: ModeEnum? = null,
    val status: RankStatus? = null,
    val totalLength: Int? = null,
    val userId: Int? = null,
    val version: String? = null,

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