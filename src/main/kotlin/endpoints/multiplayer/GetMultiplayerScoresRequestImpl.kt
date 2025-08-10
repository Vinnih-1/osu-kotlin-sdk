package endpoints.multiplayer

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.MultiplayerScores

class GetMultiplayerScoresRequestImpl(
    val roomId: Int,
    val playlistId: Int,
    val limit: Int?,
    val sort: String?,
    val cursorString: String?
) : EndpointRequest<MultiplayerScores> {

    override fun endpoint(): String = "rooms/${roomId}/playlist/${playlistId}/scores"

    override suspend fun request(client: HttpClient): MultiplayerScores {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("sort", sort)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<MultiplayerScores>(response.bodyAsText())
    }
}