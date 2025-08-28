package endpoints

import enums.Ruleset
import enums.RankingType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class RankingEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getKudosuRanking() = runTest {
        val ranking = api.getKudosuRanking()
        assertNotNull(ranking)
    }

    @Test
    fun getRankings() = runTest {
        val rankings = api.getRanking(RankingType.PERFORMANCE, Ruleset.OSU)
        assertNotNull(rankings)
    }

    @Test
    fun getSpotlights() = runTest {
        val spotlights = api.getSpotlights()
        assertNotNull(spotlights)
    }
}