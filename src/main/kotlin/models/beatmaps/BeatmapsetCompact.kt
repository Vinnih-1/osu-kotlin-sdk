package models.beatmaps

import enums.ModeEnum
import kotlinx.serialization.Serializable
import models.users.UserCompact

@Serializable
open class BeatmapsetCompact(
    val artist: String? = null,
    val artistUnicode: String? = null,
    val covers: Beatmapset.Covers? = null,
    val creator: String? = null,
    val favouriteCount: Int? = null,
    val id: Int? = null,
    val nsfw: Boolean? = null,
    val offset: Int? = null,
    val playCount: Int? = null,
    val previewUrl: String? = null,
    val source: String? = null,
    val status: String? = null,
    val spotlight: Boolean? = null,
    val title: String? = null,
    val titleUnicode: String? = null,
    val userId: Int? = null,
    val video: Boolean? = null,

    /**
     * Optional attributes
     *
     * Following are attributes which may be additionally included in the response.
     * Relevant endpoints should list them if applicable.
     */
    val beatmaps: List<Beatmap>? = null,
    val currentNominations: List<Beatmapset.Nomination>? = null,
    val currentUserAttributes: String? = null,
    val description: Description? = null,
    val discussions: String? = null,
    val events: String? = null,
    val genre: Genre? = null,
    val hasFavourited: Boolean? = null,
    val language: Language? = null,
    val nominations: String? = null,
    val packTags: List<String>? = null,
    val ratings: List<Int>? = null,
    val recentFavourites: List<UserCompact>? = null,
    val relatedUsers: List<UserCompact>? = null,
    val relatedTags: List<Tag>? = null,
    val user: UserCompact? = null,
    val trackId: Int? = null
) {
    @Serializable
    data class Tag(
        val id: Int,
        val name: String,
        val rulesetId: ModeEnum? = null,
        val description: String,
        val createdAt: String? = null,
        val updatedAt: String? = null
    )

    @Serializable
    data class Language(
        val id: Int,
        val name: String
    )

    @Serializable
    data class Genre(
        val id: Int,
        val name: String
    )

    @Serializable
    data class Description(
        val description: String
    )
}