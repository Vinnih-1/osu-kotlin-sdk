package endpoints.requests.beatmapset_discussions

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmapset_discussions.BeatmapsetDiscussionPostsResponse
import enums.BeatmapsetDiscussionPostTypes
import enums.Sort
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetBeatmapsetDiscussionPostsRequestImpl(
    val beatmapsetDiscussionId: String?,
    val limit: Int?,
    val page: Int?,
    val sort: Sort?,
    val types: List<BeatmapsetDiscussionPostTypes>?,
    val userId: String?,
    val withDeleted: String?
) : EndpointRequest<BeatmapsetDiscussionPostsResponse> {

    override fun endpoint(): String = "beatmapsets/discussions/posts"

    override suspend fun request(client: HttpClient): BeatmapsetDiscussionPostsResponse {
        val response = client.get(this.url) {
            parameter("beatmapset_discussion_id", beatmapsetDiscussionId)
            parameter("limit", limit)
            parameter("page", page)
            parameter("sort", sort)
            types?.forEach { parameter("types[]", it.toString().lowercase()) }
            parameter("user", userId)
            parameter("with_delete", withDeleted)
        }
        return json.decodeFromString<BeatmapsetDiscussionPostsResponse>(response.bodyAsText())
    }
}