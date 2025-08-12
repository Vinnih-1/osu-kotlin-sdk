package models.multiplayer

import kotlinx.serialization.Serializable
import models.scores.Score

@Serializable
data class MultiplayerScores(
    val cursorString: String? = null,
    val params: Params,
    val scores: List<Score>,
    val total: Int? = null,
    val userScore: Score? = null
) {

    @Serializable
    data class Params(
        val limit: Int,
        val sort: String
    )
}