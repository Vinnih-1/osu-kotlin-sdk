package models

import ModeEnum
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val coverUrl: String,
    val discord: String?,
    val hasSupported: Boolean,
    val interests: String?,
    val joinDate: String,
    val location: String?,
    val maxBlocks: Int,
    val maxFriends: Int,
    val occupation: String?,
    val playmode: ModeEnum,
    val playstyle: List<String>,
    val postCount: Int,
    val profileHue: Int?,
    val title: String?,
    val titleUrl: String?,
    val twitter: String?,
    val website: String?
) : UserCompact()