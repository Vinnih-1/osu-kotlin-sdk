package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFails

class OAuthTokensEndpointTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun revokeToken() = runTest {
        api.revokeToken()

        assertFails {
            api.getForumListing()
        }
    }
}