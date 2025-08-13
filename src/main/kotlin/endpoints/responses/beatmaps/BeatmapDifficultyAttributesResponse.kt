package endpoints.responses.beatmaps

import kotlinx.serialization.Serializable
import models.beatmaps.BeatmapDifficultyAttributes

@Serializable
data class BeatmapDifficultyAttributesResponse(
    val attributes: BeatmapDifficultyAttributes
)