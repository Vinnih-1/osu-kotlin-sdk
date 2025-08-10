package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class MatchEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getMatchesListing() = runTest {
        val matches = api.getMatchesListing()
        matches.matches.forEach { println(it.id) }
        assertNotNull(matches)
    }

    @Test
    fun getMatch() = runTest {
        val match = api.getMatch(118934554)
        assertNotNull(match)
    }
}