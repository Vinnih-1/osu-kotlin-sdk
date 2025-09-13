package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ForumEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getTopicListing() = runTest {
        api.getTopicListing().topics.firstOrNull()?.also { topic ->
            assertNotNull(topic)
            api.getTopicAndPosts(topic.id)
        }
    }

    @Test
    fun getForumListing() = runTest {
        val forums = api.getForumListing()
        assertNotNull(forums)
    }

    @Test
    fun getForumAndTopics() = runTest {
        val forums = api.getForumAndTopics(2)
        assertNotNull(forums)
    }

    @Test
    fun createTopic() = runTest {
        api.createTopic(
            title = "My First Topic",
            body = "Hi there! This is my first topic from my osu application",
            forumId = 52
        ).also { (topic, post) ->
            // Edit the topic
            api.editTopic(topic.id, "My First Topic (Edited)").also { assertNotNull(it) }

            // Reply the topic
            api.replyTopic(topic.id, "This is my reply!").also { assertNotNull(it) }

            // Edit the post
            api.editPost(post.id, "This is my post! (Edited)").also { assertNotNull(it) }
        }
    }
}