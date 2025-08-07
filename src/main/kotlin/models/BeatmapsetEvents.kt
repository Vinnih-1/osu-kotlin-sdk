import kotlinx.serialization.Serializable
import models.Beatmapset
import models.UserCompact

@Serializable
data class BeatmapsetEvents(
    val id: Int,
    val type: String,
    val comment: Comment? = null,
    val createdAt: String,
    val userId: Int? = null,
    val beatmapset: Beatmapset? = null,
) {

    @Serializable
    data class Comment(
        val beatmapDiscussionId: Int? = null,
        val beatmapDiscussionPostId: Int? = null,
        val beatmapId: Int? = null,
        val beatmapVersion: String? = null,
        val newUserId: Int? = null,
        val newUserUsername: String? = null,
        val newUsers: List<UserCompact>? = null,
        val createdAt: String? = null,
        val userId: Int? = null,
    )
}