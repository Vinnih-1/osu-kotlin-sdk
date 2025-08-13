package endpoints.requests.ranking

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import enums.ModeEnum
import enums.RankingType
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.rankings.Rankings

class GetRankingRequestImpl(
    val rankingType: RankingType,
    val mode: ModeEnum,
    val country: String?,
    val cursor: Rankings.Cursor?,
    val filter: String?,
    val spotlight: String?,
    val variant: String?
) : EndpointRequest<Rankings> {

    override fun endpoint(): String = "rankings/${mode.ruleset}/${rankingType.type}"

    override suspend fun request(client: HttpClient): Rankings {
        val response = client.get(this.url) {
            parameter("country", country)
            parameter("cursor", cursor)
            parameter("filter", filter)
            parameter("spotlight", spotlight)
            parameter("variant", variant)
        }
        return json.decodeFromString<Rankings>(response.bodyAsText())
    }
}