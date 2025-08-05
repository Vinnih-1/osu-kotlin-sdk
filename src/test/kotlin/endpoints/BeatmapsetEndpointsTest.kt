package endpoints

import OsuKDK
import credentials.Authorization
import credentials.GrantType
import credentials.ScopesEnum
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapsetEndpointsTest {

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
    fun searchBeatmapset() = runTest {
        val beatmapsets = api.searchBeatmapset()
        assertNotNull(beatmapsets)
    }

    @Test
    fun getBeatmapset() = runTest {
        val beatmapset = api.getBeatmapset(414543)
        assertNotNull(beatmapset)
    }

    @Test
    fun getBeatmapsetEvents() = runTest {
        val events = api.getBeatmapsetEvents()
        assertNotNull(events)
    }
}