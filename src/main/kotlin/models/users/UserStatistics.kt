package models.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatistics(

    @SerialName("count_100") val count100: Int,
    @SerialName("count_300") val count300: Int,
    @SerialName("count_50") val count50: Int,

    val countMiss: Int,
    val countryRank: Int? = null,
    val gradeCounts: GradeCounts,
    val hitAccuracy: Float,
    val isRanked: Boolean,
    val level: Level,
    val maximumCombo: Int,
    val playCount: Int,
    val playTime: Int,
    val pp: Float,
    val ppExp: Float,
    val globalRank: Int?,
    val globalRankExp: Int?,
    val rankedScore: Long,
    val totalHits: Long,
    val totalScore: Long,
    val replaysWatchedByOthers: Int,
) {
    @Serializable
    data class Level(
        val current: Int,
        val progress: Float
    )

    @Serializable
    data class GradeCounts(
        val a: Int,
        val s: Int,
        val sh: Int,
        val ss: Int,
        val ssh: Int
    )
}