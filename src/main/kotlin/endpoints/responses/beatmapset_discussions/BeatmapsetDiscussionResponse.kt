package endpoints.responses.beatmapset_discussions

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmap
import models.beatmaps.Beatmapset
import models.beatmaps.BeatmapsetDiscussion
import models.users.User

@Serializable
data class BeatmapsetDiscussionResponse(
    val beatmaps: List<Beatmap>,
    val beatmapsets: List<Beatmapset>,
    val discussions: List<BeatmapsetDiscussion>,
    val includedDiscussions: List<BeatmapsetDiscussion>,
    val users: List<User>,
    val reviewsConfig: ReviewsConfig,
    val cursorString: String
) {
    @Serializable
    data class ReviewsConfig(
        val maxBlocks: Int,
    )
}