package models.scores

import kotlinx.serialization.Serializable

@Serializable
data class Mod(
    val acronym: String,
    val settings: Settings? = null
) {

    @Serializable
    data class Settings (
        val speedChange: Float? = null,
        val adjustPitch: Boolean? = null,
        val restart: Boolean? = null,
        val accuracyJudgeMode: String? = null,
        val circleSize: Float? = null,
        val approachRate: Float? = null,
        val drainRate: Float? = null,
        val overallDifficulty: Float? = null,
    )
}