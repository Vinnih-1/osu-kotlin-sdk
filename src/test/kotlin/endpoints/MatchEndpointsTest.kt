package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class MatchEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getMatchesListing() = runTest {
        val matches = api.getMatchesListing().matches.get(0)
        assertNotNull(matches)
        val match = api.getMatch(matches.id)
        assertNotNull(match)
    }
}