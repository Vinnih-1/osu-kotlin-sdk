package models

import kotlinx.serialization.Serializable

@Serializable
data class Spotlight(
    val endDate: String,
    val id: Int,
    val modeSpecific: Boolean,
    val participantCount: Int? = null,
    val name: String,
    val startDate: String,
    val type: String
) {
}