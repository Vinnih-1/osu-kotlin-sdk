package models.users

import enums.Ruleset
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val cover: Cover? = null,
    val discord: String? = null,
    val hasSupported: Boolean? = false,
    val interests: String? = null,
    val joinDate: String? = null,
    val location: String? = null,
    val maxBlocks: Int? = null,
    val maxFriends: Int? = null,
    val occupation: String? = null,
    val playmode: Ruleset? = null,
    val playstyle: List<String>? = null,
    val postCount: Int? = null,
    val profileHue: Int? = null,
    val title: String? = null,
    val titleUrl: String? = null,
    val twitter: String? = null,
    val website: String? = null
) : UserCompact() {

    @Serializable
    data class Cover(
        val customUrl: String?,
        val url: String?,
        val id: String? = null
    )
}