package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapsetEndpointsTest {

    val api = OsuApiProvider.api

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