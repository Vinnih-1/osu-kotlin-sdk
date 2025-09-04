package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RelationType {
    @SerialName("friend") FRIEND,
    @SerialName("block") BLOCK
}