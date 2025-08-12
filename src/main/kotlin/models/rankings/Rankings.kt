package models.rankings

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmapset
import models.users.UserStatistics

@Serializable
data class Rankings(
    val beatmapsets: List<Beatmapset>? = null,
    val ranking: List<UserStatistics>,
    val spotlight: Spotlight? = null,
    val total: Int,
    val cursor: Cursor
) {

    @Serializable
    data class Cursor(
        val page: String? = null
    )
}