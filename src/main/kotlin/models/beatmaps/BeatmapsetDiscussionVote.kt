package models.beatmaps

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapsetDiscussionVote(
    val beatmapsetDiscussionId: Int? = null,
    val score: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val userId: Int? = null,
    val id: Int? = null,
)