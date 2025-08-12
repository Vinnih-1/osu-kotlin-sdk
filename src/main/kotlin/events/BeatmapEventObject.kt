import kotlinx.serialization.Serializable

@Serializable
data class BeatmapEventObject(
    val title: String,
    val url: String
)