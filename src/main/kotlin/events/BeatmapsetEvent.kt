package events

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapsetEvent(val title: String, val url: String)