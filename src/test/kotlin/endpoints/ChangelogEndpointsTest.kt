package endpoints

import OsuKDK
import credentials.Authorization
import credentials.GrantType
import credentials.ScopesEnum
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ChangelogEndpointsTest {

    companion object {
        private val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            runBlocking { Authorization(
                clientId,
                clientSecret,
                grantType = GrantType.AUTHORIZATION_CODE,
                scope = listOf(ScopesEnum.PUBLIC, ScopesEnum.IDENTIFY)).create() }
        }
    }

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