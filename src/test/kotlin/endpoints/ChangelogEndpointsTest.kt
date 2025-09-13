package endpoints

import OsuApiProvider
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ChangelogEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getChangelogBuild() = runTest {
        val build = api.getChangelogBuild("stable40", "20250702.1")
        assertNotNull(build)
    }

    @Test
    fun getChangelogListing() = runTest {
        val build = api.getChangelogListing()
        assertNotNull(build)
    }

    @Test
    fun lookupChangelogBuild() = runTest {
        val build = api.lookupChangelogBuild("2025.730.0")
        assertNotNull(build)
    }
}