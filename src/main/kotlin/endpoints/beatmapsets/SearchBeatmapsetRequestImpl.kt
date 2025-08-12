package endpoints.beatmapsets

import OsuKDK.Companion.json
import endpoints.EndpointRequest
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

@Serializable
data class SearchBeatmapsetResponse(
    val beatmapsets: List<Beatmapset>,
    val search: Search,
    val recommendedDifficulty: Float,
    val error: String? = null,
    val total: Int,
    val cursorString: String? = null
) {

    @Serializable
    data class Search(
        val sort: String
    )
}