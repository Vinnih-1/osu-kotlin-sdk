package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class HomeSearchEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun homeSearch() = runTest {
        val result = api.search()
        assertNotNull(result)
    }
}