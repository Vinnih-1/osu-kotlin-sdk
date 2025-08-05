import credentials.Credentials
import endpoints.beatmap_packs.GetBeatmapPackRequestImpl
import endpoints.beatmap_packs.GetBeatmapPacksRequestImpl
import endpoints.beatmaps.*
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionPostsRequestImpl
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionVotesRequestImpl
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionsRequestImpl
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
class OsuKDK(var credentials: Credentials, val apiVersion: Int? = 20240529) {

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
     *  Get Beatmap Packs
     *
     *  Returns a list of beatmap packs.
     *
     *  @param type (Optional) of the beatmap packs to be returned. Defaults to standard.
     *  @param cursor (Optional) for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmap-packs
     */
    suspend fun getBeatmapPacks(type: BeatmapPack.Type? = BeatmapPack.Type.STANDARD, cursor: String? = ""): BeatmapPackResponse {
        return GetBeatmapPacksRequestImpl(type, cursor).request(client)
    }

    /**
     *  Get Beatmap Pack
     *
     *  Gets the beatmap pack for the specified beatmap pack tag.
     *
     *  @param pack The tag of the beatmap pack to be returned.
     *  @param legacyOnly (Optional) Whether or not to consider lazer scores for user completion data. Defaults to false.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmap-pack
     */
    suspend fun getBeatmapPack(pack: String, legacyOnly: Boolean? = false): BeatmapPack {
        return GetBeatmapPackRequestImpl(pack, legacyOnly).request(client)
    }

    /**
     *  Get a User Beatmap score
     *
     *  Return a User's score on a Beatmap
     *
     *  @param beatmapId Id of the Beatmap.
     *  @param userId Id of the User.
     *  @param legacyOnly (Optional) Whether or not to exclude lazer scores. Defaults to false.
     *  @param mode (Optional) The Ruleset to get scores for.
     *  @param mods (Optional) An array of matching Mods, or none // TODO.
     *
     *  The position returned depends on the requested mode and mods.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-score
     */
    suspend fun getUserBeatmapScore(
        beatmapId: Int,
        userId: Int,
        legacyOnly: Boolean? = false,
        mode: ModeEnum? = ModeEnum.OSU,
        mods: String? = ""
    ): BeatmapUserScore {
        return GetUserBeatmapScoreRequestImpl(beatmapId, userId, legacyOnly, mode, mods).request(client)
    }

    /**
     *  Get a User Beatmap scores
     *
     *  Return a User's scores on a Beatmap
     *
     *  @param beatmapId Id of the Beatmap.
     *  @param userId Id of the User.
     *  @param legacyOnly (Optional) Whether or not to exclude lazer scores. Defaults to false.
     *  @param mode (Optional) The Ruleset to get scores for.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-scores
     */
    suspend fun getUserBeatmapScores(
        beatmapId: Int,
        userId: Int,
        legacyOnly: Boolean? = false,
        mode: ModeEnum? = ModeEnum.OSU
    ): List<Score> {
        return GetUserBeatmapScoresRequestImpl(beatmapId, userId, legacyOnly, mode).request(client)
    }

    /**
     *  Get Beatmap scores
     *
     *  Returns the top scores for a beatmap. Depending on user
     *  preferences, this may only show legacy scores.
     *
     *  @param beatmapId Id of the Beatmap.
     *  @param legacyOnly (Optional) Whether or not to exclude lazer scores. Defaults to false.
     *  @param mode (Optional) The Ruleset to get scores for.
     *  @param mods (Optional) An array of matching Mods, or none // TODO.
     *  @param type (Optional) Beatmap score ranking type // TODO.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmap-scores
     */
    suspend fun getBeatmapScores(
        beatmapId: Int,
        legacyOnly: Boolean? = false,
        mode: ModeEnum? = ModeEnum.OSU,
        mods: String? = "",
        type: String? = ""
    ): BeatmapScores {
        return GetBeatmapScoresRequestImpl(beatmapId, legacyOnly, mode, mods, type).request(client)
    }

    /**
     *  Get Beatmaps
     *
     *  Returns a list of beatmaps.
     *
     *  @param ids Beatmap IDs to be returned. Specify once for each beatmap
     *  ID requested. Up to 50 beatmaps can be requested at once.
     *
     *  This method will return an empty list if don't specify ids
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmaps
     */
    suspend fun getBeatmaps(ids: List<Int>? = listOf()): List<Beatmap> {
        return GetBeatmapsRequestImpl(ids).request(client)
    }

    /**
     *  Get Beatmap
     *
     *  Gets beatmap data for the specified beatmap ID.
     *
     *  @param beatmapId The ID of the beatmap.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmap
     */
    suspend fun getBeatmap(beatmapId: Int): Beatmap {
        return GetBeatmapRequestImpl(beatmapId).request(client)
    }

    /**
     *  Get Beatmap Attributes
     *
     *  Returns difficulty attributes of beatmap with specific mode and mods combination.
     *
     *  @param beatmapId The ID of the beatmap.
     *  @param mods array of mods
     *  @param mode Ruleset of the difficulty attributes. Only valid if it's the
     *  beatmap ruleset or the beatmap can be converted to the specified ruleset.
     *  Defaults to ruleset of the specified beatmap.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmap-attributes
     */
    suspend fun getBeatmapAttributes(beatmapId: Int, mods: List<ScoreLegacy.Mod>, mode: ModeEnum): BeatmapDifficultyAttributes {
        return GetBeatmapAttributesRequestImpl(beatmapId, mods, mode).request(client)
    }

    suspend fun getBeatmapsetDiscussionPosts(
        beatmapsetDiscussionId: String? = null,
        limit: Int? = 100,
        page: Int? = null,
        sort: BeatmapsetDiscussionPost.Sort? = BeatmapsetDiscussionPost.Sort.NEWEST,
        types: List<BeatmapsetDiscussionPost.Types>? = listOf(BeatmapsetDiscussionPost.Types.REPLY),
        userId: String? = null,
        withDeleted: String? = null
    ): BeatmapsetDiscussionPostsResponse {
        return GetBeatmapsetDiscussionPostsRequestImpl(beatmapsetDiscussionId, limit, page, sort, types, userId, withDeleted).request(client)
    }

    suspend fun getBeatmapsetDiscussionVotes(
        beatmapsetDiscussionId: String? = null,
        limit: Int? = 100,
        page: Int? = null,
        receiver: String? = null,
        score: String? = null,
        sort: BeatmapsetDiscussionPost.Sort? = BeatmapsetDiscussionPost.Sort.NEWEST,
        userId: String? = null,
        withDeleted: String? = null
    ): BeatmapsetDiscussionVotesResponse {
        return GetBeatmapsetDiscussionVotesRequestImpl(beatmapsetDiscussionId, limit, page, receiver, score, sort, userId, withDeleted).request(client)
    }

    suspend fun getBeatmapsetDiscussion(
        beatmapId: String? = null,
        beatmapsetId: String? = null,
        beatmapsetStatus: String? = null,
        limit: Int? = 100,
        messageTypes: List<String>? = null,
        onlyUnresolved: Boolean? = null,
        page: Int? = null,
        sort: BeatmapsetDiscussionPost.Sort? = BeatmapsetDiscussionPost.Sort.NEWEST,
        userId: String? = null,
        withDeleted: String? = null,
        cursorString: String? = null,
    ): BeatmapsetDiscussionResponse {
        return GetBeatmapsetDiscussionsRequestImpl(
            beatmapId,
            beatmapsetId,
            beatmapsetStatus,
            limit, messageTypes,
            onlyUnresolved,
            page,
            sort,
            userId,
            withDeleted,
            cursorString
            ).request(client)
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