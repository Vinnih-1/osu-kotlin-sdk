package endpoints.responses.beatmapsets

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmapset

@Serializable
data class SearchBeatmapsetResponse(
    val beatmapsets: List<Beatmapset>,
    val search: Search,
    val recommendedDifficulty: Float? = null,
    val error: String? = null,
    val total: Int,
    val cursorString: String? = null
) {

    @Serializable
    data class Search(
        val sort: String
    )
}