package models.beatmaps

import enums.BeatmapRankStatus
import enums.Ruleset
import kotlinx.serialization.Serializable

@Serializable
open class BeatmapCompact(
    val beatmapsetId: Int? = null,
    val difficultyRating: Float? = null,
    val id: Int? = null,
    val mode: Ruleset? = null,
    val status: BeatmapRankStatus? = null,
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
    val failtimes: Beatmap.Failtimes? = null,
    val maxCombo: Int? = null,
    val beatmapOwner: List<Beatmap.BeatmapOwner>? = null
)