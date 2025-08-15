package enums

import kotlinx.serialization.Serializable

@Serializable
enum class RankingType(val type: String) {
    CHARTS("charts"),
    COUNTRY("country"),
    PERFORMANCE("performance"),
    SCORE("score")
}