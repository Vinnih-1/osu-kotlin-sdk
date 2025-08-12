package models.comments

import kotlinx.serialization.Serializable
import models.users.User

@Serializable
data class CommentBundle(
    val commentableMeta: List<CommentableMeta>,
    val comments: List<Comment>,
    val cursor: Cursor? = null,
    val hasMore: Boolean,
    val hasMoreId: Int? = null,
    val includedComments: List<Comment>? = null,
    val pinnedComments: List<Comment>? = null,
    val sort: String,
    val topLevelCount: Int? = null,
    val total: Int? = null,
    val userFollow: Boolean? = null,
    val userVotes: List<Int>? = null,
    val users: List<User>? = null
) {
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
}

@Serializable
data class Cursor(
    val id: Int? = null,
    val createdAt: String? = null
)