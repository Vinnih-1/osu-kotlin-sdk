package models

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapUserScore(
    val position: Int,
    val score: Score
) {
}