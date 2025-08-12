package enums

import kotlinx.serialization.Serializable

@Serializable
enum class CommentSort(val value: String) {
    NEW("new"),
    OLD("old"),
    TOP("top")
}