package models

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapPlayCount(
    val beatmapId: Int? = null,
    val count: Int? = null,
    val beatmap: Beatmap.BeatmapCompact? = null,
    val beatmapset: Beatmapset
) {

    enum class GetBeatmapType {
        FAVOURITE, GRAVEYARD, GUEST, LOVED, MOST_PLAYED, NOMINATED, PENDING, RANKED
    }
}