package endpoints.responses.forums

import kotlinx.serialization.Serializable
import models.forums.ForumPost
import models.forums.ForumTopic

@Serializable
data class CreateTopicResponse(
    val topic: ForumTopic,
    val post: ForumPost
)