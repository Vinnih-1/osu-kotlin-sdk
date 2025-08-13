package endpoints.responses.beatmapset_discussions

import kotlinx.serialization.Serializable
import models.beatmaps.BeatmapsetDiscussion
import models.beatmaps.BeatmapsetDiscussionVote
import models.users.UserCompact

@Serializable
data class BeatmapsetDiscussionVotesResponse(
    val discussions: List<BeatmapsetDiscussion>,
    val votes: List<BeatmapsetDiscussionVote>,
    val users: List<UserCompact>,
    val cursorString: String
)