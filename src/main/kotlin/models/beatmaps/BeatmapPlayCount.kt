package models.beatmaps

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapPlayCount(
    val beatmapId: Int? = null,
    val count: Int? = null,
    val beatmap: BeatmapCompact? = null,
    val beatmapset: Beatmapset
)