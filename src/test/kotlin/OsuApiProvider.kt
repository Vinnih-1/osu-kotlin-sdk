import credentials.Auth
import enums.Scopes
import enums.Version

class OsuApiProvider {
    companion object {
        val api: OsuKDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            Auth(clientId, clientSecret)
                .apply {
                    redirectUri = "http://localhost:3914"
                    scopes = listOf(Scopes.PUBLIC, Scopes.FRIENDS_READ)
                    version = Version.V2022_06_08
                }.create()
        }
    }
}