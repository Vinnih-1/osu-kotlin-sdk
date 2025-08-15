package models.matches

import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val id: Long,
    val startTime: String,
    val endTime: String? = null,
    val name: String
)