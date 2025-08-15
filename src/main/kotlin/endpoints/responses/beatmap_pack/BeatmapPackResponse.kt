package endpoints.responses.beatmap_pack

import kotlinx.serialization.Serializable
import models.beatmaps.BeatmapPack

@Serializable
data class BeatmapPackResponse(
    val beatmapPacks: List<BeatmapPack>,
    val cursorString: String
)