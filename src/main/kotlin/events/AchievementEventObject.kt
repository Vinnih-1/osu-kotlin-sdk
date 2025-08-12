package events

import enums.ModeEnum
import kotlinx.serialization.Serializable

@Serializable
data class AchievementEventObject(
    val id: Int,
    val iconUrl: String,
    val name: String,
    val grouping: String,
    val ordering: Int,
    val slug: String,
    val description: String,
    val modeEnum: ModeEnum? = null,
    val instructions: String? = null
)