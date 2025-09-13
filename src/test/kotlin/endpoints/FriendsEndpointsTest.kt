package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class FriendsEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getFriends() = runTest {
        api.getFriends().also { assertNotNull(it) }
    }
}