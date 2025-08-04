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

    /**
     *  Get User
     *
     *  This endpoint returns the detail of specified user.
     *
     *  @param userId Id of the user.
     *  @param mode (Optional) osu mode will be used if not specified.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-user
     */
    suspend fun getUser(userId: Int, mode: ModeEnum = ModeEnum.OSU): User {
        return GetUserRequestsImpl(userId, mode).request(client)
    }

    /**
     *  Get Users
     *
     *  Returns list of users.
     *
     *  @param ids User id to be returned. Specify once for each user id requested. Up to 50 users can be requested at once.
     *  @param includeVariantStatistics (Optional) Whether to additionally include statistics variants (default: true).
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-users
     */
    suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean? = true): List<User> {
        return GetUsersRequestImpl(ids, includeVariantStatistics).request(client)
    }

    /**
     *  Get User Kudosu
     *
     *  Returns kudosu history
     *
     *  @param userId Id of the user.
     *  @param limit (Optional) Maximum number of results. defaults to 50
     *  @param offset (Optional) Result offset for pagination. defaults to 0
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-user-kudosu
     */
    suspend fun getUserKudosu(userId: Int, limit: Int? = 50, offset: String? = "0"): List<KudosuHistory> {
        return GetUserKudosuRequestImpl(userId, limit, offset).request(client)
    }

    /**
     *  Get User Scores
     *
     *  This method returns the scores of specified user.
     *
     *  @param userId Id of the user.
     *  @param type (Optional) Score type.
     *  @param legacyOnly (Optional) Whether or not to exclude lazer scores. Defaults to false
     *  @param includeFails (Optional) Only for recent scores, include scores of failed plays. Set to true to include them. Defaults to false.
     *  @param mode (Optional) Mode of the scores to be returned. Defaults to osu mode.
     *  @param offset (Optional) Result offset for pagination. default is 0
     *  @param limit (Optional) Maximum number of results. default is 100
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-user-scores
     */
    suspend fun getUserScore(
        userId: Int,
        type: Score.ScoreType? = Score.ScoreType.RECENT,
        legacyOnly: Boolean? = false,
        includeFails: Boolean? = false,
        mode: ModeEnum? = ModeEnum.OSU,
        offset: Int? = 0,
        limit: Int? = 100
    ): List<Score> {
        return GetUserScoresRequestImpl(userId, type, legacyOnly, includeFails, mode, offset, limit).request(client)
    }

    /**
     *  Get User Beatmaps
     *
     *  Returns the beatmaps of specified user.
     *
     *  @param userId Id of the user.
     *  @param type Beatmap type.
     *  @param offset (Optional) Result offset for pagination. default is 0
     *  @param limit (Optional) Maximum number of results. default is 100
     *
     *  When type is MOST_PLAYED only Beatmapset will not be null
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-user-beatmaps
     */
    suspend fun getUserBeatmaps(
        userId: Int,
        type: BeatmapPlayCount.GetBeatmapType,
        offset: Int? = 0,
        limit: Int? = 100
    ): List<BeatmapPlayCount> {
        return GetUserBeatmapsRequestImpl(userId, type, offset, limit).request(client)
    }

    /**
     *  Get User Recent Activity
     *
     *  Returns recent activity.
     *
     *  @param userId Id of the user.
     *  @param offset (Optional) Result offset for pagination. default is 0
     *  @param limit (Optional) Maximum number of results. default is 100
     *
     *  https://osu.ppy.sh/docs/index.html#get-user-recent-activity
     */
    suspend fun getUserRecentActivity(userId: Int, offset: Int? = 0, limit: Int? = 100): List<Event> {
        return GetUserRecentActivityRequestImpl(userId, offset, limit).request(client)
    }

    /**
     *  Search Beatmaps Passed
     *
     *  Searches for the Beatmaps a User has passed by Beatmapset.
     *
     *  @param userId Id of the user.
     *  @param beatmapsetIds (Optional) The list of Beatmapset.
     *  @param excludeConverts (Optional) Whether or not to exclude converts.
     *  @param isLegacy (Optional) Whether or not to consider legacy scores. Leave empty for all scores.
     *  @param noDiffReduction (Optional) Whether or not to exclude diff reduction mods. Defaults to true.
     *  @param rulesetId (Optional) The Ruleset ID. Leave empty for all rulesets.
     *
     *  https://osu.ppy.sh/docs/index.html#search-beatmaps-passed
     */
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

    /**
     *  Get Own Data
     *
     *  Similar to Get User but with authenticated user (token owner) as user id.
     *
     *  @param mode (Optional) osu mode will be used if not specified.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-own-data
     */
    suspend fun getOwnData(mode: ModeEnum? = ModeEnum.OSU): User {
        return GetOwnDataRequestImpl(mode).request(client)
    }
}