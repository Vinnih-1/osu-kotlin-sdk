package enums

import kotlinx.serialization.Serializable

@Serializable
enum class BeatmapPackType(tag: String) {
    STANDARD("S"),
    FEATURED("F"),
    TOURNAMENT("P"),
    LOVED("L"),
    CHART("R"),
    THEME("T"),
    ARTIST("A")
}