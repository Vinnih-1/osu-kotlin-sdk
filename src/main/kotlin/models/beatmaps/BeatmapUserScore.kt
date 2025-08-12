package models.beatmaps

import kotlinx.serialization.Serializable
import models.scores.Score

@Serializable
data class BeatmapUserScore(
    val position: Int,
    val score: Score
)