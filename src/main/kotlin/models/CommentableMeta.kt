package models

import kotlinx.serialization.Serializable

@Serializable
data class CommentableMeta(
    val id: Int? = null,
    val ownerId: Int? = null,
    val ownerTitle: String? = null,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
    val currentUserAttributes: CurrentUserAttributes? = null
) {

    @Serializable
    data class CurrentUserAttributes(
        val canNewCommentReason: String? = null,
    ) {
    }
}