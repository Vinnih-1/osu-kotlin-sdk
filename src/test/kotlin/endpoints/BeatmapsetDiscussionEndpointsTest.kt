package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapsetDiscussionEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getBeatmapsetDiscussionsPosts() = runTest {
        val discussions = api.getBeatmapsetDiscussionPosts()
        assertNotNull(discussions)
    }

    @Test
    fun getBeatmapsetDiscussionsVotes() = runTest {
        val votes = api.getBeatmapsetDiscussionVotes()
        assertNotNull(votes)
    }

    @Test
    fun getBeatmapsetDiscussions() = runTest {
        val discussions = api.getBeatmapsetDiscussion()
        assertNotNull(discussions)
    }
}