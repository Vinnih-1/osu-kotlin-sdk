package models.beatmaps

import enums.ModeEnum
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
    val storyboard: Boolean? = null,
    val submittedDate: String? = null,
    val tags: String? = null
) : BeatmapsetComapct() {

    @Serializable
    data class Nomination(
        val beatmapsetId: Int,
        val rulesets: List<ModeEnum>? = null,
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