package endpoints.responses.rankings

import kotlinx.serialization.Serializable
import models.users.User

@Serializable
data class KudosuRankingResponse(
    val ranking: List<User>
)