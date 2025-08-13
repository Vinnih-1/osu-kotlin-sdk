package endpoints.responses.matches

import kotlinx.serialization.Serializable
import models.matches.Match

@Serializable
data class MatchesResponse(
    val matches: List<Match>,
    val cursorString: String? = null,
    val params: Params,
) {

    @Serializable
    data class Params(
        val limit: Int,
        val sort: String
    )
}