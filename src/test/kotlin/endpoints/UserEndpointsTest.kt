package endpoints

import OsuKDK
import credentials.Authorization
import credentials.GrantType
import credentials.ScopesEnum
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import models.BeatmapPlayCount
import models.Score
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserEndpointsTest {


    companion object {
        private val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")
            val auth = Authorization(
                clientId,
                clientSecret,
                grantType = GrantType.AUTHORIZATION_CODE,
                scope = listOf(ScopesEnum.PUBLIC, ScopesEnum.IDENTIFY))

            runBlocking { OsuKDK(auth.fetchCredentials()) }
        }

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
    fun getUserRecentActivity() = runTest {
        val events = api.getUserRecentActivity(21009314)
        assertNotNull(events)
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

    @Test
    fun getOwnData() = runTest {
        val ownData = api.getOwnData()
        assertNotNull(ownData.sessionVerified)
        assertNotNull(ownData.statisticsRulesets)
        assertEquals(ownData.discord, "zuminho_")
    }
}