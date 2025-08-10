package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class EventEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getEvents() = runTest {
        val events = api.getEvents()
        println(OsuKDK.json.encodeToString(events))
        assertNotNull(events)
    }
}