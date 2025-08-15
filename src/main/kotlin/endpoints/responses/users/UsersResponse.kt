package endpoints.responses.users

import kotlinx.serialization.Serializable
import models.users.User

@Serializable
data class UsersResponse(
    val users: List<User>
)