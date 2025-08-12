package endpoints.beatmaps

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import enums.ModLegacy
import enums.ModeEnum
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import models.beatmaps.BeatmapDifficultyAttributes

class GetBeatmapAttributesRequestImpl(
    val beatmapId: Int,
    val mods: List<ModLegacy>,
    val mode: ModeEnum
) : EndpointRequest<BeatmapDifficultyAttributes> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/attributes"

    override suspend fun request(client: HttpClient): BeatmapDifficultyAttributes {
        val body = buildJsonObject {
            put("mods", json.encodeToJsonElement(mods))
            put("ruleset", json.encodeToJsonElement(mode))
        }
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(body.toString())
        }
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject["attributes"]
        return json.decodeFromString<BeatmapDifficultyAttributes>(jsonObject.toString())
    }
}