package endpoints.requests.beatmapsets

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmapsets.BeatmapsetEventsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetBeatmapsetEvents : EndpointRequest<BeatmapsetEventsResponse> {

    override fun endpoint(): String = "beatmapsets/events"

    override suspend fun request(client: HttpClient): BeatmapsetEventsResponse {
        val response = client.get(this.url)
        return json.decodeFromString<BeatmapsetEventsResponse>(response.bodyAsText())
    }
}