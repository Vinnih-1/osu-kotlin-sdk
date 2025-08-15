package endpoints.responses.rankings

import kotlinx.serialization.Serializable
import models.rankings.Spotlight

@Serializable
data class SpotlightsRankingResponse(
    val spotlights: List<Spotlight>
)