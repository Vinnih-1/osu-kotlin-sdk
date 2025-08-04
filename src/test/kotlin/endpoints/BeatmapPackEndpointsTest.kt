package endpoints

import OsuKDK
import credentials.Authorization
import credentials.GrantType
import credentials.ScopesEnum
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapPackEndpointsTest {

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
    fun getBeatmapPacks() = runTest {
        val response = api.getBeatmapPacks()
        response.beatmapPacks.forEach { println(it.name) }
        assertNotNull(response)
    }

    @Test
    fun getBeatmapPack() = runTest {
        val response = api.getBeatmapPack("S1674")
        assertNotNull(response)
    }
}