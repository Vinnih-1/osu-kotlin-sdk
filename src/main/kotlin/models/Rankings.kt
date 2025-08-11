package models

import kotlinx.serialization.Serializable

@Serializable
data class Rankings(
    val beatmapsets: List<Beatmapset>? = null,
    val ranking: List<UserCompact.UserStatistics>,
    val spotlight: Spotlight? = null,
    val total: Int,
    val cursor: Cursor
) {

    @Serializable
    data class Cursor(
        val page: String? = null
    ) {
    }
}