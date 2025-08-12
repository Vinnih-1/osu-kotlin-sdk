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
                grantType = GrantType.AUTHORIZATION_CODE,
                scope = listOf(ScopesEnum.PUBLIC, ScopesEnum.FORUM_WRITE)).create() }
        }
    }
}