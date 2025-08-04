import credentials.Credentials
import endpoints.user.*
import events.impl.Event
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import models.*
import models.Beatmap
import models.User

@OptIn(ExperimentalSerializationApi::class)
class OsuKDK(credentials: Credentials, val apiVersion: Int? = 20240529) {

    val client = HttpClient {
        defaultRequest {
            header(HttpHeaders.Authorization, "${credentials.tokenType} ${credentials.accessToken}")
            header("x-api-version", apiVersion)
        }
    }

    companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            explicitNulls = true
            namingStrategy = JsonNamingStrategy.Builtins.SnakeCase
        }
    }

    suspend fun getUser(userId: Int, mode: ModeEnum = ModeEnum.OSU): User {
        return GetUserRequestsImpl(userId, mode).request(client)
    }

    suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean? = true): List<User> {
        return GetUsersRequestImpl(ids, includeVariantStatistics).request(client)
    }

    suspend fun getUserKudosu(userId: Int, limit: Int? = 50, offset: String? = "0"): List<KudosuHistory> {
        return GetUserKudosuRequestImpl(userId, limit, offset).request(client)
    }

    suspend fun getUserScore(
        userId: Int,
        type: Score.ScoreType? = Score.ScoreType.RECENT,
        legacyOnly: Boolean? = false,
        includeFails: Boolean? = false,
        offset: Int? = 0,
        limit: Int? = 100
    ): List<Score> {
        return GetUserScoresRequestImpl(userId, type, legacyOnly, includeFails, offset, limit).request(client)
    }

    suspend fun getUserBeatmaps(
        userId: Int,
        type: BeatmapPlayCount.GetBeatmapType,
        offset: Int? = 0,
        limit: Int? = 100
    ): List<BeatmapPlayCount> {
        return GetUserBeatmapsRequestImpl(userId, type, offset, limit).request(client)
    }

    suspend fun getUserRecentActivity(userId: Int, offset: Int? = 0, limit: Int? = 100): List<Event> {
        return GetUserRecentActivityRequestImpl(userId, offset, limit).request(client)
    }

    suspend fun searchBeatmapsPassed(
        userId: Int,
        beatmapsetIds: List<Int>? = listOf(),
        excludeConverts: Boolean? = false,
        isLegacy: Boolean? = true,
        noDiffReduction: Boolean? = true,
        rulesetId: Int? = null
    ): List<Beatmap> {
        return SearchBeatmapsPassedRequestImpl(userId, beatmapsetIds, excludeConverts, isLegacy, noDiffReduction, rulesetId).request(client)
    }

    suspend fun getOwnData(modeEnum: ModeEnum? = ModeEnum.OSU): User {
        return GetOwnDataRequestImpl(modeEnum).request(client)
    }
}