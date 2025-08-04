package events

import kotlinx.serialization.Serializable

@Serializable
data class Beatmapset(val title: String, val url: String) {
}