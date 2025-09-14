package endpoints

import OsuApiProvider
import enums.BeatmapPlaycountType
import enums.ScoreType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getUser() = runTest {
        val user = api.getUser("21009314")
        assertEquals(user.username, "Zuminho")
    }

    @Test
    fun getUserScore() = runTest {
        val score = api.getUserScore(21009314, ScoreType.BEST, legacyOnly = true, limit = 1)
        assertNotNull(score.get(0).pp)
    }

    @Test
    fun getUserBeatmaps() = runTest {
        val beatmaps = api.getUserBeatmaps(21009314, BeatmapPlaycountType.FAVOURITE, limit = 5)
        assert(beatmaps.isNotEmpty())
    }

    @Test
    fun getUserRecentActivity() = runTest {
        val events = api.getUserRecentActivity(21009314)
        assertNotNull(events)
    }

    @Test
    fun searchBeatmapsPassed() = runTest {
        val beatmaps = api.searchBeatmapsPassed(21009314, listOf(414543, 896080))
        assert(beatmaps.beatmapsPassed.isNotEmpty())
    }

    @Test
    fun getUsers() = runTest {
        val users = api.getUsers(listOf("21009314", "8128469", "7562902"))
        assertEquals(users.users.size, 3)
    }

    @Test
    fun getUserKudosu() = runTest {
        val kudosu = api.getUserKudosu(3388410).get(0)
        assertNotNull(kudosu.id)
    }

    @Test
    fun getOwnData() = runTest {
        val ownData = api.getOwnData()
        assertNotNull(ownData.sessionVerified)
        assertNotNull(ownData.statisticsRulesets)
        assertEquals(ownData.discord, "zuminho_")
    }
}