package endpoints.responses.beatmapset_discussions

import kotlinx.serialization.Serializable
import models.beatmaps.Beatmapset
import models.beatmaps.BeatmapsetDiscussion
import models.beatmaps.BeatmapsetDiscussionPost
import models.users.User

@Serializable
data class BeatmapsetDiscussionPostsResponse(
    val beatmapsets: List<Beatmapset>,
    val discussions: List<BeatmapsetDiscussion>,
    val posts: List<BeatmapsetDiscussionPost>,
    val users: List<User>,
    val cursorString: String
)