import credentials.Authorization
import enums.Scopes

class OsuApiProvider {
    companion object {
        val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            Authorization(clientId, clientSecret)
                .apply {
                    redirectUri = "http://localhost:3914"
                    scopes = listOf(Scopes.PUBLIC, Scopes.CHAT_READ, Scopes.CHAT_WRITE_MANAGE, Scopes.CHAT_WRITE)
                }.create()
        }
    }
}