package endpoints.responses.beatmapsets

import kotlinx.serialization.Serializable
import models.beatmaps.BeatmapsetEvents
import models.users.UserCompact

@Serializable
data class BeatmapsetEventsResponse(
    val events: List<BeatmapsetEvents>,
    val reviewsConfig: ReviewsConfig? = null,
    val users: List<UserCompact>
) {
    @Serializable
    data class ReviewsConfig(val maxBlocks: Int)
}