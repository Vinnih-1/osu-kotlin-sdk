package credentials

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val tokenType: String? = null,
    val expiresIn: Int? = null,
)