import kotlinx.serialization.Serializable

@Serializable
data class UserEvent(
    val username: String,
    val url: String,
    val previousName: String? = null
)