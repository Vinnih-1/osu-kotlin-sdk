package endpoints.requests.beatmapsets

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmapsets.SearchBeatmapsetResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import models.beatmaps.Beatmapset

class SearchBeatmapsetRequestImpl(val cursorString: String?) : EndpointRequest<SearchBeatmapsetResponse> {

    override fun endpoint(): String = "beatmapsets/search"

    override suspend fun request(client: HttpClient): SearchBeatmapsetResponse {
        val response = client.get(this.url) {
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<SearchBeatmapsetResponse>(response.bodyAsText())
    }
}