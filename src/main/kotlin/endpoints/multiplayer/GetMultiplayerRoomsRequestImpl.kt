package endpoints.multiplayer

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.Room

class GetMultiplayerRoomsRequestImpl(
    val limit: Int?,
    val mode: String?,
    val seasonId: String?,
    val sort: String?,
    val typeGroup: String?
) : EndpointRequest<List<Room>> {

    override fun endpoint(): String = "rooms"

    override suspend fun request(client: HttpClient): List<Room> {
        val response = client.get(this.url) {
            parameter("limit", limit)
            parameter("mode", mode)
            parameter("season_id", seasonId)
            parameter("sort", sort)
            parameter("type_group", typeGroup)
        }
        return json.decodeFromString<List<Room>>(response.bodyAsText())
    }
}