import kotlinx.serialization.Serializable

@Serializable
data class UserEventObject(
    val username: String,
    val url: String,
    val previousName: String? = null
)