package endpoints.requests.beatmaps

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmaps.BeatmapsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetBeatmapsRequestImpl(val ids: List<Int>?) : EndpointRequest<BeatmapsResponse> {

    override fun endpoint(): String = "beatmaps"

    override suspend fun request(client: HttpClient): BeatmapsResponse {
        val response = client.get(this.url) {
            ids?.forEach { parameter("ids[]", it) }
        }
        return json.decodeFromString<BeatmapsResponse>(response.bodyAsText())
    }
}