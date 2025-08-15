package endpoints.responses.forums

import kotlinx.serialization.Serializable
import models.forums.ForumPost
import models.forums.ForumTopic

@Serializable
data class ForumTopicAndPostsResponse (
    val posts: List<ForumPost>,
    val topic: ForumTopic,
    val cursorString: String? = null
)