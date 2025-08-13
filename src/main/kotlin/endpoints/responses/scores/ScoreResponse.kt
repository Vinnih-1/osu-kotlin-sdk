package endpoints.responses.scores

import kotlinx.serialization.Serializable
import models.scores.Score

@Serializable
data class ScoreResponse(
    val scores: List<Score>,
    val cursorString: String? = null
)