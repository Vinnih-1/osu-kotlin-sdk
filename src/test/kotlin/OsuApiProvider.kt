import credentials.Authorization
import enums.GrantType
import enums.ScopesEnum
import kotlinx.coroutines.runBlocking

class OsuApiProvider {
    companion object {
        val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            runBlocking { Authorization(
                clientId,
                clientSecret,
                "http://localhost:3914",
                scope = listOf(ScopesEnum.PUBLIC, ScopesEnum.FORUM_WRITE)).create() }
        }
    }
}