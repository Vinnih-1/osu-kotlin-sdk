import kotlinx.serialization.Serializable

@Serializable
data class Beatmap(
    val title: String,
    val url: String
)