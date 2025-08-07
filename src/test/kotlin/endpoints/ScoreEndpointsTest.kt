package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ScoreEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun downloadScore() = runTest {
        val bytes = api.downloadScore(4459998279, true)
        assertNotNull(bytes)
    }

    @Test
    fun getScore() = runTest {
        val scores = api.getScore()
        assertNotNull(scores)
    }
}