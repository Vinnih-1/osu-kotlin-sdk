package endpoints.responses.forums

import kotlinx.serialization.Serializable
import models.forums.ForumTopic

@Serializable
data class ForumTopicResponse(
    val topics: List<ForumTopic>,
    val cursorString: String
)