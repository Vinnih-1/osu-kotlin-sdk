import credentials.Auth
import enums.Scopes
import enums.Version
import sdk.OsuSDK
import sdk.OsuSDKSync

class OsuApiProvider {
    companion object {
        val apiAsync: OsuSDK by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            Auth(clientId, clientSecret)
                .apply {
                    redirectUri = "http://localhost:3914"
                    scopes = listOf(Scopes.PUBLIC, Scopes.FRIENDS_READ)
                    version = Version.V2025_04_10
                }.createAsync()
        }

        val apiSync: OsuSDKSync by lazy {
            val clientId = System.getenv("CLIENT_ID").toInt()
            val clientSecret = System.getenv("CLIENT_SECRET")

            Auth(clientId, clientSecret)
                .apply {
                    redirectUri = "http://localhost:3914"
                    scopes = listOf(Scopes.PUBLIC, Scopes.FRIENDS_READ)
                    version = Version.V2025_04_10
                }.createSync()
        }
    }
}