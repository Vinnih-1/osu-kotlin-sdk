import credentials.Authorization
import enums.ScopesEnum

class OsuApiProvider {
    companion object {
        val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            Authorization(clientId, clientSecret, "http://localhost:3914",
                scopes = listOf(ScopesEnum.PUBLIC, ScopesEnum.FORUM_WRITE)).create()
        }
    }
}