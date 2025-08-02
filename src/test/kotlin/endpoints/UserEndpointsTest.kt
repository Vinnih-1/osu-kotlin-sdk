package endpoints

import OsuKDK
import credentials.Authorization
import kotlinx.coroutines.test.runTest
import models.BeatmapPlayCount
import models.Beatmapset
import models.Score
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserEndpointsTest {

    private lateinit var api: OsuKDK

    @BeforeTest
    fun setup() = runTest {
        val clientId = System.getenv("CLIENT_ID").toInt()
        val clientSecret = System.getenv("CLIENT_SECRET")
        val auth = Authorization(clientId, clientSecret)
        api = OsuKDK(auth.fetchCredentials())
    }

    @Test
    fun getUser() = runTest {
        val user = api.getUser(21009314)
        assertEquals(user.username, "Zuminho")
    }

    @Test
    fun getUserScore() = runTest {
        val score = api.getUserScore(21009314, Score.ScoreType.BEST, legacyOnly = true, limit = 1)
        assertNotNull(score.get(0).pp)
    }

    @Test
    fun getUserBeatmaps() = runTest {
        val beatmaps = api.getUserBeatmaps(21009314, BeatmapPlayCount.GetBeatmapType.FAVOURITE, limit = 5)
        assert(beatmaps.isNotEmpty())
    }

    @Test
    fun searchBeatmapsPassed() = runTest {
        val beatmaps = api.searchBeatmapsPassed(21009314, listOf(414543, 896080))
        assert(beatmaps.isNotEmpty())
    }

    @Test
    fun getUsers() = runTest {
        val users = api.getUsers(listOf("21009314", "8128469", "7562902"))
        assertTrue(users.size == 3)
    }

    @Test
    fun getUserKudosu() = runTest {
        val kudosu = api.getUserKudosu(3388410).get(0)
        assertNotNull(kudosu.id)
    }
}