package credentials

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val accessToken: String = "",
    val refreshToken: String = "",
    val tokenType: String = "",
    val expiresIn: Int = 0,
)