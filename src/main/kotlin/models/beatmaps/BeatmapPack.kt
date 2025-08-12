package models.beatmaps

import kotlinx.serialization.Serializable

@Serializable
data class BeatmapPack(
    val author: String,
    val date: String,
    val name: String,
    val noDiffReduction: Boolean,
    val rulesetId: Int? = null,
    val tag: String,
    val url: String,
    val beatmapsets: List<Beatmapset>? = null,
    val userCompletionData: UserCompletionData? = null
) {

    @Serializable
    data class UserCompletionData(
        val completed: Boolean,
        val beatmapsetIds: List<Int>
    )
}

@Serializable
data class BeatmapPackResponse(
    val beatmapPacks: List<BeatmapPack>,
    val cursorString: String
)