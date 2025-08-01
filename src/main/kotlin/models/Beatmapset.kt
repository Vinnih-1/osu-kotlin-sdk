package models

import ModeEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beatmapset(
    val avaliability: Avaliability? = null,
    val bpm: Float? = null,
    val canBeHyped: Boolean? = null,
    val deletedAt: String? = null,
    val discussionEnabled: Boolean? = null,
    val discussionLocked: Boolean? = null,
    val hype: Hype? = null,
    val isScoreable: Boolean? = null,
    val lastUpdate: String? = null,
    val legacyThreadUrl: String? = null,
    val nominationsSumary: NominationsSumary? = null,
    val ranked: Int? = null,
    val rankedDate: String? = null,
    val rating: Float? = null,
    val source: String? = null,
    val storyboard: Boolean? = null,
    val submittedDate: String? = null,
    val tags: String? = null
) {

    @Serializable
    data class BeatmapsetComapct(
        val artist: String,
        val artistUnicode: String,
        val covers: Covers,
        val creator: String,
        val favouriteCount: Int,
        val id: Int,
        val nsfw: Boolean,
        val offset: Int,
        val playCount: Int,
        val previewUrl: String,
        val source: String,
        val status: String,
        val spotlight: Boolean,
        val title: String,
        val titleUnicode: String,
        val userId: Int,
        val video: Boolean,

        /**
         * Optional attributes
         *
         * Following are attributes which may be additionally included in the response.
         * Relevant endpoints should list them if applicable.
         */

        val beatmapset: List<Beatmap>? = null,
        val currentNominations: List<Nomination>? = null,
        val currentUserAttributes: String? = null,
        val description: String? = null,
        val discussions: String? = null,
        val events: String? = null,
        val genre: String? = null,
        val hasFavourited: Boolean? = null,
        val language: String? = null,
        val nominations: String? = null,
        val packTags: List<String>? = null,
        val ratings: String? = null,
        val recentFavourites: String? = null,
        val relatedUsers: String? = null,
        val user: String? = null,
        val trackId: Int? = null
    )

    @Serializable
    data class Nomination(
        val beatmapsetId: Int,
        val rulesets: List<ModeEnum>,
        val reset: Boolean,
        val userId: Int
    )

    @Serializable
    data class Avaliability(
        val downloadDisabled: Boolean,
        val moreInformation: String?,
    )

    @Serializable
    data class Hype(
        val current: Int,
        val required: Int
    )

    @Serializable
    data class NominationsSumary(
        val current: Int,
        val required: Int
    )

    @Serializable
    data class Covers(
        val cover: String,
        @SerialName("cover@2x") val cover2x: String,

        val card: String,
        @SerialName("card@2x") val card2x: String,

        val list: String,
        @SerialName("list@2x") val list2x: String,

        val slimcover: String,
        @SerialName("slimcover@2x") val slimcover2x: String
    )
}