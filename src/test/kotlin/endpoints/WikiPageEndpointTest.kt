package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class WikiPageEndpointTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun getWikiPage() = runTest {
        val page = api.getWikiPage("pt-br", "Client/File_formats")
        assertNotNull(page)
    }
}