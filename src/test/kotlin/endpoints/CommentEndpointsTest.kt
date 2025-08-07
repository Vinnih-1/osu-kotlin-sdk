package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommentEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getComments() = runTest {
        val comments = api.getComments()
        assertNotNull(comments)
    }
}