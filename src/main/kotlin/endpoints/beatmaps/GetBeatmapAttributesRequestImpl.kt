package endpoints.beatmaps

import ModeEnum
import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import models.BeatmapDifficultyAttributes
import models.ScoreLegacy

class GetBeatmapAttributesRequestImpl(
    val beatmapId: Int,
    val mods: List<ScoreLegacy.Mod>,
    val mode: ModeEnum
) : EndpointRequest<BeatmapDifficultyAttributes> {

    override fun endpoint(): String = "beatmaps/${beatmapId}/attributes"

    override suspend fun request(client: HttpClient): BeatmapDifficultyAttributes {
        val response = client.post(this.url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(RequestObject(mods, mode)))
        }
        val jsonObject = json.parseToJsonElement(response.bodyAsText()).jsonObject["attributes"]
        return json.decodeFromString<BeatmapDifficultyAttributes>(jsonObject.toString())
    }

    @Serializable
    data class RequestObject(val mods: List<ScoreLegacy.Mod>, val ruleset: ModeEnum)
}