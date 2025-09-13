package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class NewsEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getNewsListing() = runTest {
        val news = api.getNewsListing()
        assertNotNull(news)
    }

    @Test
    fun getNewsPost() = runTest {
        val news = api.getNewsPost("2025-07-29-summer-mapping-contest-2-results")
        assertNotNull(news)
    }
}