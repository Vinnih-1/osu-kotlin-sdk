package models

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val commentableId: Int,
    val commentableType: String,
    val createdAt: String,
    val deletedAt: String? = null,
    val editedAt: String? = null,
    val editedById: Int? = null,
    val legacyName: String? = null,
    val message: String? = null,
    val messageHtml: String? = null,
    val parentId: Int? = null,
    val pinned: Boolean,
    val repliesCount: Int,
    val updatedAt: String,
    val userId: Int,
    val votesCount: Int
)