package models

import kotlinx.serialization.Serializable

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
)

@Serializable
data class Cursor(
    val id: Int? = null,
    val createdAt: String? = null
)

@Serializable
enum class CommentSort(val value: String) {
    NEW("new"),
    OLD("old"),
    TOP("top")
}