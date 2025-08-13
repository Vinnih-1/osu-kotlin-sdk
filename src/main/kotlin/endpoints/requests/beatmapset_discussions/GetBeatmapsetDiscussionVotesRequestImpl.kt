package endpoints.requests.beatmapset_discussions

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.Sort
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.beatmaps.BeatmapsetDiscussionVotesResponse

class GetBeatmapsetDiscussionVotesRequestImpl(
    val beatmapsetDiscussionId: String?,
    val limit: Int?,
    val page: Int?,
    val receiver: String?,
    val score: String?,
    val sort: Sort?,
    val userId: String?,
    val withDeleted: String?
) : EndpointRequest<BeatmapsetDiscussionVotesResponse> {

    override fun endpoint(): String = "beatmapsets/discussions/votes"

    override suspend fun request(client: HttpClient): BeatmapsetDiscussionVotesResponse {
        val response = client.get(this.url) {
            parameter("beatmapset_discussion_id", beatmapsetDiscussionId)
            parameter("limit", limit)
            parameter("page", page)
            parameter("receiver", receiver)
            parameter("score", score)
            parameter("sort", sort)
            parameter("user", userId)
            parameter("with_delete", withDeleted)
        }
        return json.decodeFromString<BeatmapsetDiscussionVotesResponse>(response.bodyAsText())
    }
}