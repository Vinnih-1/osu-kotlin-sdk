package endpoints.responses.beatmaps

import kotlinx.serialization.Serializable
import models.scores.Score

@Serializable
data class UserBeatmapsScoresResponse(
    val scores: List<Score>
)