import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val url: String,
    val previousName: String? = null
)