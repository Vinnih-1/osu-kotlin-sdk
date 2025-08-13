package endpoints.responses.matches

import kotlinx.serialization.Serializable
import models.matches.Match
import models.matches.MatchEvent
import models.users.User

@Serializable
data class MatchResponse(
    val match: Match,
    val events: List<MatchEvent>,
    val users: List<User>,
    val firstEventId: Long,
    val latestEventId: Long
)