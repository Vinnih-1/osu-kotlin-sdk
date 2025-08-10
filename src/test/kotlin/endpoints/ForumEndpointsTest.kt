package endpoints

import endpoints.forum.TopicRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ForumEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getTopicListing() = runTest {
        val topics = api.getTopicListing()
        assertNotNull(topics)
    }

    @Test
    fun getTopicAndPosts() = runTest {
        val topic = api.getTopicAndPosts(1995583)
        assertNotNull(topic)
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
        val topic = api.createTopic(TopicRequest(
            title = "My First Topic",
            body = "Hi there! This is my first topic from my osu application",
            forumId = 2
        ))
        assertNotNull(topic)
    }

    @Test
    fun editTopic() = runTest {
        val topic = api.editTopic(2115924, "My First Topic (Edited)")
        assertNotNull(topic)
    }

    @Test
    fun replyTopic() = runTest {
        val post = api.replyTopic(2115924, "This is my reply!")
        println(post.id)
        assertNotNull(post)
    }

    @Test
    fun editPost() = runTest {
        val post = api.editPost(9990471, "This is my reply! (Edited)")
        assertNotNull(post)
    }
}