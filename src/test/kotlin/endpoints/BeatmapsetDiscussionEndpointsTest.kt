package endpoints

import OsuKDK
import credentials.Authorization
import credentials.GrantType
import credentials.ScopesEnum
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapsetDiscussionEndpointsTest {

    companion object {
        private val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            runBlocking { Authorization(
                clientId,
                clientSecret,
                grantType = GrantType.AUTHORIZATION_CODE,
                scope = listOf(ScopesEnum.PUBLIC, ScopesEnum.IDENTIFY)).create() }
        }
    }

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