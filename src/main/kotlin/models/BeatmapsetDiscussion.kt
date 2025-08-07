package models

import kotlinx.serialization.Serializable

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
) {
    enum class Sort(val value: String) {
        NEWEST("id_desc"),
        OLDEST("id_asc")
    }

    enum class Types {
        FIRST, REPLY, SYSTEM
    }
}

@Serializable
data class Discussion(
    val id: Int,
    val beatmapsetId: Int,
    val beatmapId: Int? = null,
    val userId: Int,
    val createdAt: String,
    val deletedAt: String? = null,
    val deletedById: Int? = null,
    val updatedAt: String,
    val messageType: String,
    val parentId: Int? = null,
    val timestamp: Int? = null,
    val resolved: Boolean,
    val canBeResolved: Boolean,
    val canGrantKudosu: Boolean,
    val lastPostAt: String,
    val kudosuDenied: Boolean
)

@Serializable
data class BeatmapsetDiscussionVote(
    val beatmapsetDiscussionId: Int? = null,
    val score: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userId: Int? = null,
    val id: Int? = null,
)

@Serializable
data class BeatmapsetDiscussionResponse(
    val beatmaps: List<Beatmap>,
    val beatmapsets: List<Beatmapset>,
    val discussions: List<Discussion>,
    val includedDiscussions: List<Discussion>,
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
    val discussions: List<Discussion>,
    val posts: List<BeatmapsetDiscussionPost>,
    val users: List<User>,
    val cursorString: String
)

@Serializable
data class BeatmapsetDiscussionVotesResponse(
    val discussions: List<Discussion>,
    val votes: List<BeatmapsetDiscussionVote>,
    val users: List<UserCompact>,
    val cursorString: String
)