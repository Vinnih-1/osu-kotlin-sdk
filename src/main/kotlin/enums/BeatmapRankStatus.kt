package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BeatmapRankStatus(val code: Int) {
    @SerialName("graveyard") GRAVEYARD(-2),
    @SerialName("wip") WIP(-1),
    @SerialName("pending") PENDING(0),
    @SerialName("ranked") RANKED(1),
    @SerialName("approved") APPROVED(2),
    @SerialName("qualified") QUALIFIED(3),
    @SerialName("loved") LOVED(4)
}