package endpoints.responses.beatmaps

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap

@Serializable
data class BeatmapsResponse(
    val beatmaps: List<Beatmap>
)