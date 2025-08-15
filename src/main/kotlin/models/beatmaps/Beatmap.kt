package models.beatmaps

import kotlinx.serialization.Serializable

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