package endpoints.requests.user

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.users.SearchBeatmapsPassedResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class SearchBeatmapsPassedRequestImpl(
    val userId: Int,
    val beatmapsetIds: List<Int>?,
    val excludeConverts: Boolean?,
    val isLegacy: Boolean?,
    val noDiffReduction: Boolean?,
    val rulesetId: Int?
) : EndpointRequest<SearchBeatmapsPassedResponse> {

    override fun endpoint(): String = "users/${userId}/beatmaps-passed"

    override suspend fun request(client: HttpClient): SearchBeatmapsPassedResponse {
        val response = client.get(this.url) {
            beatmapsetIds?.forEach { parameter("beatmapset_ids[]", it) }
            parameter("exclude_converts", excludeConverts)
            parameter("is_legacy", isLegacy)
            parameter("no_diff_reduction", noDiffReduction)
            parameter("ruleset_id", rulesetId)
        }
        return json.decodeFromString<SearchBeatmapsPassedResponse>(response.bodyAsText())
    }
}