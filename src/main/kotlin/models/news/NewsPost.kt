package models.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsPost(
    val author: String,
    val editUrl: String,
    val firstImage: String? = null,
    @SerialName("first_image@2x") val firstImage2x: String? = null,
    val id: Int,
    val publishedAt: String,
    val slug: String,
    val title: String,
    val updatedAt: String,
    val preview: String? = null,
    val content: String? = null,
    val navigation: Navigation? = null
) {
    @Serializable
    data class Navigation(
        val newer: NewsPost? = null,
        val older: NewsPost? = null
    )
}