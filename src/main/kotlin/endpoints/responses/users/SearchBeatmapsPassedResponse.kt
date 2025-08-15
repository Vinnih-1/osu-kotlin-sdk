package endpoints.responses.users

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap

@Serializable
data class SearchBeatmapsPassedResponse(
    val beatmapsPassed: List<Beatmap>
)