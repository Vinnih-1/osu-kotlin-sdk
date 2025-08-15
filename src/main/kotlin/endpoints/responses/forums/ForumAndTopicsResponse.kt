package endpoints.responses.forums

import kotlinx.serialization.Serializable
import models.forums.Forum
import models.forums.ForumTopic

@Serializable
data class ForumAndTopicsResponse (
    val forum: Forum,
    val topics: List<ForumTopic>,
    val pinnedTopics: List<ForumTopic>
)