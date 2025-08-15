package models.forums

import kotlinx.serialization.Serializable

@Serializable
data class ForumPost(
    val createdAt: String,
    val deletedAt: String? = null,
    val editedAt: String? = null,
    val editedById: Int? = null,
    val forumId: Int,
    val id: Int,
    val topicId: Int,
    val userId: Int,
    val body: Body? = null
) {

    @Serializable
    data class Body(
        val html: String,
        val raw: String
    )
}