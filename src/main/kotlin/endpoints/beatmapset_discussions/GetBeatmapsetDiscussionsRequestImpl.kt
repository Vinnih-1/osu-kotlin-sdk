package endpoints.beatmapset_discussions

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import enums.Sort
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.BeatmapsetDiscussionResponse

class GetBeatmapsetDiscussionsRequestImpl(
    val beatmapId: String?,
    val beatmapsetId: String?,
    val beatmapsetStatus: String?,
    val limit: Int?,
    val messageTypes: List<String>?,
    val onlyUnresolved: Boolean?,
    val page: Int?,
    val sort: Sort??,
    val userId: String?,
    val withDeleted: String?,
    val cursorString: String?,
) : EndpointRequest<BeatmapsetDiscussionResponse> {

    override fun endpoint(): String = "beatmapsets/discussions"

    override suspend fun request(client: HttpClient): BeatmapsetDiscussionResponse {
        val response = client.get(this.url) {
            parameter("beatmap_id", beatmapId)
            parameter("beatmapset_id", beatmapsetId)
            parameter("beatmapset_status", beatmapsetStatus)
            parameter("limit", limit)
            parameter("message_types[]", messageTypes)
            parameter("only_unresolved", onlyUnresolved)
            parameter("page", page)
            parameter("sort", sort)
            parameter("user", userId)
            parameter("with_deleted", withDeleted)
            parameter("cursor_string", cursorString)
        }
        return json.decodeFromString<BeatmapsetDiscussionResponse>(response.bodyAsText())
    }
}