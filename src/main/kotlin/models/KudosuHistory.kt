package models

import kotlinx.serialization.Serializable

@Serializable
data class KudosuHistory(
    val id: Int,
    val action: String,
    val amount: Int,
    val model: String,
    val createdAt: String,
    val giver: Giver?,
    val post: Post
) {

    @Serializable
    data class Giver(
        val url: String,
        val username: String
    )

    @Serializable
    data class Post(
        val url: String?,
        val title: String
    )
}