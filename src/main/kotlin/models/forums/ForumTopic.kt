package models.forums

import kotlinx.serialization.Serializable

@Serializable
data class ForumTopic(
    val createdAt: String,
    val deletedAt: String? = null,
    val firstPostId: Int,
    val forumId: Int,
    val id: Int,
    val isLocked: Boolean,
    val lastPostId: Int,
    val poll: Poll? = null,
    val postCount: Int,
    val title: String,
    val type: String,
    val updatedAt: String,
    val userId: Int
) {

    @Serializable
    data class Poll(
        val allowVoteChange: Boolean,
        val endedAt: String? = null,
        val hideIncompleteResults: Boolean,
        val lastVoteAt: String? = null,
        val maxVotes: Int,
        val options: List<PollOptions>,
        val startedAt: String,
        val title: Title,
        val totalVoteCount: Int
    ) {

        @Serializable
        data class PollOptions(
            val id: Int,
            val text: Text,
            val voteCount: Int? = null,
        ) {

            @Serializable
            data class Text(
                val bbcode: String,
                val html: String
            )
        }

        @Serializable
        data class Title(
            val bbcode: String,
            val html: String
        )
    }
}