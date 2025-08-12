package events

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapsetEventObject(val title: String, val url: String)