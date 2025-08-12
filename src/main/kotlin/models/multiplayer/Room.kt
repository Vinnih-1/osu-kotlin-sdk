package models.multiplayer

import kotlinx.serialization.Serializable
import models.scores.Score
import models.beatmaps.Beatmap
import models.users.UserCompact

@Serializable
data class Room(
    val id: Int,
    val name: String,
    val category: String,
    val status: String,
    val type: String,
    val userId: Int,
    val startsAt: String,
    val endsAt: String,
    val maxAttempts: Int? = null,
    val participantsCount: Int? = null,
    val channelId: Int,
    val active: Boolean,
    val hasPassword: Boolean,
    val queueMode: String,
    val autoSkip: Boolean,
    val currentPlaylistItem: CurrentPlaylistItem,
    val difficultyRange: DifficultyRange,
    val host: UserCompact,
    val playlistItemStats: PlaylistItemStats,
    val recentParticipants: List<UserCompact>
) {

    @Serializable
    data class CurrentPlaylistItem(
        val id: Int,
        val roomId: Int,
        val beatmapId: Int,
        val createdAt: String,
        val rulesetId: Int,
        val allowedMods: List<Score.Mod>,
        val requiredMods: List<Score.Mod>,
        val freestyle: Boolean,
        val expired: Boolean,
        val ownerId: Int,
        val playlistOrder: String? = null,
        val playedAt: String? = null,
        val beatmap: Beatmap
    )

    @Serializable
    data class DifficultyRange(
        val max: Float,
        val min: Float
    )

    @Serializable
    data class PlaylistItemStats(
        val countActive: Int,
        val countTotal: Int,
        val rulesetIds: List<Int>
    )
}