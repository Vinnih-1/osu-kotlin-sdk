package models

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapScores(
    val scores: List<Score>,
    val userScores: BeatmapUserScore? = null
) {
}