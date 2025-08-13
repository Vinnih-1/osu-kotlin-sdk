package endpoints.requests.beatmaps

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.beatmaps.BeatmapDifficultyAttributesResponse
import enums.ModLegacy
import enums.ModeEnum
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement

class GetBeatmapAttributesRequestImpl(
    val beatmapId: Int,
    val mods: List<ModLegacy>,
    val mode: ModeEnum
) : EndpointRequest<BeatmapDifficultyAttributesResponse> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/attributes"

    override suspend fun request(client: HttpClient): BeatmapDifficultyAttributesResponse {
        val body = buildJsonObject {
            put("mods", json.encodeToJsonElement(mods))
            put("ruleset", json.encodeToJsonElement(mode))
        }
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(body.toString())
        }
        return json.decodeFromString<BeatmapDifficultyAttributesResponse>(response.bodyAsText())
    }
}