package models.beatmaps

import kotlinx.serialization.Serializable
import models.users.User
import models.users.UserCompact

@Serializable
data class BeatmapsetDiscussionPost(
    val beatmapsetDiscussionId: Int? = null,
    val createdAt: String? = null,
    val deletedAt: String? = null,
    val deletedById: Int? = null,
    val id: Int? = null,
    val lastEditorId: Int? = null,
    val message: String? = null,
    val system: Boolean? = null,
    val updatedAt: String? = null,
    val userId: Int? = null
)

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

@Serializable
data class BeatmapsetDiscussionPostsResponse(
    val beatmapsets: List<Beatmapset>,
    val discussions: List<BeatmapsetDiscussion>,
    val posts: List<BeatmapsetDiscussionPost>,
    val users: List<User>,
    val cursorString: String
)

@Serializable
data class BeatmapsetDiscussionVotesResponse(
    val discussions: List<BeatmapsetDiscussion>,
    val votes: List<BeatmapsetDiscussionVote>,
    val users: List<UserCompact>,
    val cursorString: String
)