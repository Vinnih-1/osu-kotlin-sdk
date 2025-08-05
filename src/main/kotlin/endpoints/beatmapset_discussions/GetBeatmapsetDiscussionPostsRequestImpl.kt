package endpoints.beatmapset_discussions

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.BeatmapsetDiscussionPost
import models.BeatmapsetDiscussionPostsResponse

class GetBeatmapsetDiscussionPostsRequestImpl(
    val beatmapsetDiscussionId: String?,
    val limit: Int?,
    val page: Int?,
    val sort: BeatmapsetDiscussionPost.Sort?,
    val types: List<BeatmapsetDiscussionPost.Types>?,
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