package models.forums

import kotlinx.serialization.Serializable

@Serializable
data class Forum(
    val id: Int,
    val name: String,
    val description: String,
    val subforums: List<Forum>? = null
)