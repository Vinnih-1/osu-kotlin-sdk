package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class MultiplayerEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getMultiplayerRooms() = runTest {
        val room = api.getMultiplayerRooms(limit = 1).get(0)
        assertNotNull(room)
        val scores = api.getMultiplayerScores(room.id, room.currentPlaylistItem.id)
        assertNotNull(scores)
    }
}