package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapPackEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getBeatmapPacks() = runTest {
        val response = api.getBeatmapPacks()
        assertNotNull(response)
    }

    @Test
    fun getBeatmapPack() = runTest {
        val response = api.getBeatmapPack("S1674")
        assertNotNull(response)
    }
}