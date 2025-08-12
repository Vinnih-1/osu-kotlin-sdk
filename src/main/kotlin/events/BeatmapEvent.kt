import kotlinx.serialization.Serializable

@Serializable
data class BeatmapEvent(
    val title: String,
    val url: String
)