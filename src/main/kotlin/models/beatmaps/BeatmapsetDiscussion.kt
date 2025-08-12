package models.beatmaps

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapsetDiscussion(
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