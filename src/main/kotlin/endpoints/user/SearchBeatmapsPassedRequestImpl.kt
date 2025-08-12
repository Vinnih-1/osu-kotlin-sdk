package endpoints.user

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonObject
import models.beatmaps.Beatmap

class SearchBeatmapsPassedRequestImpl(
    val userId: Int,
    val beatmapsetIds: List<Int>?,
    val excludeConverts: Boolean?,
    val isLegacy: Boolean?,
    val noDiffReduction: Boolean?,
    val rulesetId: Int?
) : EndpointRequest<List<Beatmap>> {

    override fun endpoint(): String = "users/${userId}/beatmaps-passed"

    override suspend fun request(client: HttpClient): List<Beatmap> {
        val response = client.get(this.url) {
            beatmapsetIds?.forEach { parameter("beatmapset_ids[]", it) }
            parameter("exclude_converts", excludeConverts)
            parameter("is_legacy", isLegacy)
            parameter("no_diff_reduction", noDiffReduction)
            parameter("ruleset_id", rulesetId)
        }
        val jsonElement = json.parseToJsonElement(response.bodyAsText())
        return json.decodeFromString<List<Beatmap>>(jsonElement.jsonObject["beatmaps_passed"].toString())
    }
}