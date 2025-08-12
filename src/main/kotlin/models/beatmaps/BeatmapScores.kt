package models.beatmaps

import kotlinx.serialization.Serializable
import models.scores.Score

@Serializable
data class BeatmapScores(
    val scores: List<Score>,
    val userScores: BeatmapUserScore? = null
)