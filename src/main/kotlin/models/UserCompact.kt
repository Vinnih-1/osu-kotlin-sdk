package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class UserCompact(
    val id: Int = 0,
    val username: String = "",
    var profileColour: String? = null,
    val avatarUrl: String = "",
    val countryCode: String = "",
    val lastVisit: String? = null,
    val defaultGroup: String? = null,
    val isActive: Boolean = false,
    val isBot: Boolean = false,
    val isDeleted: Boolean = false,
    val isOnline: Boolean = false,
    val isSupporter: Boolean = false,
    val pmFriendsOnly: Boolean = false,

    /**
     * Optional attributes
     *
     * Following are attributes which may be additionally included in the response.
     * Relevant endpoints should list them if applicable.
     */
    val accountHistory: List<UserAccountHistory>? = null,
    val activeTournamentBanner: ProfileBanner? = null,
    val activeTournamentBanners: List<ProfileBanner>? = null,
    val badges: List<UserBadge>? = null,
    val beatmapPlaycountsCount: Int? = null,
    val favouriteBeatmapsetCount: Int? = null,
    val followUserMapping: List<Int>? = null,
    val followerCount: Int? = null,
    val graveyardBeatmapsetCount: Int? = null,
    val groups: List<Group>? = null,
    val guestBeatmapsetCount: Int? = null,
    val isRestricted: Boolean? = null,
    val kudosu: Kudosu? = null,
    val lovedBeatmapsetCount: Int? = null,
    val mappingFollowerCount: Int? = null,
    val monthlyPlaycounts: List<UserMonthlyPlaycount>? = null,
    val nominatedBeatmapsetCount: Int? = null,
    val rankHighest: RankHighest? = null,
    val scoresBestCount: Int? = null,
    val scoresFirstCount: Int? = null,
    val scoresRecentCount: Int? = null,
    val sessionVerified: Boolean? = null,
    val statistics: UserStatistics? = null
) {
    @Serializable
    class RankHighest(
        val rank: Int,
        val updatedAt: String
    )

    @Serializable
    data class Kudosu(
        val available: Int,
        val total: Int
    )

    @Serializable
    class UserMonthlyPlaycount(
        val startDate: String,
        val count: Int
    )

    @Serializable
    data class ProfileBanner(
        val id: Int,
        val tournamentId: Int,
        val image: String?,
        @SerialName("image@2x") val image2x: String?
    )

    @Serializable
    data class UserAccountHistory(
        val description: String?,
        val id: Int,
        val length: Int,
        val permanent: Boolean,
        val timestamp: String,
        val type: String
    )

    @Serializable
    data class UserBadge(
        val awardedAt: String,
        val description: String,
        @SerialName("image@2x_url") val image2xUrl: String,
        val imageUrl: String,
        val url: String
    )

    @Serializable
    data class Group(
        val colour: String?,
        val hasListing: Boolean,
        val hasPlaymodes: Boolean,
        val id: Int,
        val identifier: String,
        val isProbationary: Boolean,
        val name: String,
        val shortName: String,
        val description: Description?
    ) {
        @Serializable
        data class Description(
            val html: String,
            val markdown: String
        )
    }

    @Serializable
    data class UserStatistics(

        @SerialName("count_100") val count100: Int,
        @SerialName("count_300") val count300: Int,
        @SerialName("count_50") val count50: Int,

        val countMiss: Int,
        val countryRank: Int?,
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
}