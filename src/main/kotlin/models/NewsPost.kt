package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsPost(
    val author: String,
    @SerialName("edit_url")
    val editUrl: String,
    @SerialName("first_image")
    val firstImage: String? = null,
    @SerialName("first_image@2x")
    val firstImage2x: String? = null,
    val id: Int,
    @SerialName("published_at")
    val publishedAt: String,
    val slug: String,
    val title: String,
    @SerialName("updated_at")
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