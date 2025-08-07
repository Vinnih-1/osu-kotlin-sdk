package endpoints.user

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.BeatmapPlayCount
import models.Beatmapset

class GetUserBeatmapsRequestImpl(
    val userId: Int,
    val type: BeatmapPlayCount.GetBeatmapType,
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
            BeatmapPlayCount.GetBeatmapType.MOST_PLAYED -> json.decodeFromString<List<BeatmapPlayCount>>(response.bodyAsText())
            else -> {
                json.decodeFromString<List<Beatmapset>>(response.bodyAsText()).map { BeatmapPlayCount(beatmapset = it) }
            }
        }
    }
}