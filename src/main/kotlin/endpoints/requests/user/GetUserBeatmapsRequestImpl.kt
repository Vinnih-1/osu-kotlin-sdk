package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.BeatmapPlaycountType
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.BeatmapPlayCount
import models.beatmaps.Beatmapset

class GetUserBeatmapsRequestImpl(
    val userId: Int,
    val type: BeatmapPlaycountType,
    val offset: Int?,
    val limit: Int?
) : EndpointRequest<List<BeatmapPlayCount>> {

    override fun endpoint(): String = "users/${userId}/beatmapsets/${type.toString().lowercase()}"

    override suspend fun request(client: HttpClient): List<BeatmapPlayCount> {
        val response = client.get(this.url) {
            parameter("offset", offset)
            parameter("limit", limit)
        }

        return when (type) {
            BeatmapPlaycountType.MOST_PLAYED -> json.decodeFromString<List<BeatmapPlayCount>>(response.bodyAsText())
            else -> {
                json.decodeFromString<List<Beatmapset>>(response.bodyAsText()).map { BeatmapPlayCount(beatmapset = it) }
            }
        }
    }
}