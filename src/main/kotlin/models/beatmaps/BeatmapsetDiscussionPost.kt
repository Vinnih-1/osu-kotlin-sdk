package models.beatmaps

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
)