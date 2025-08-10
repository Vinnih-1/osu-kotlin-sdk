import credentials.Credentials
import endpoints.beatmap_packs.GetBeatmapPackRequestImpl
import endpoints.beatmap_packs.GetBeatmapPacksRequestImpl
import endpoints.beatmaps.*
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionPostsRequestImpl
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionVotesRequestImpl
import endpoints.beatmapset_discussions.GetBeatmapsetDiscussionsRequestImpl
import endpoints.beatmapsets.BeatmapsetEventsResponse
import endpoints.beatmapsets.GetBeatmapsetEvents
import endpoints.beatmapsets.GetBeatmapsetRequestImpl
import endpoints.beatmapsets.SearchBeatmapsetRequestImpl
import endpoints.beatmapsets.SearchBeatmapsetResponse
import endpoints.changelog.BuildResponse
import endpoints.changelog.GetChangelogBuildRequestImpl
import endpoints.changelog.GetChangelogListingRequestImpl
import endpoints.changelog.LookupChangelogBuildRequestImpl
import endpoints.comments.GetCommentRequestImpl
import endpoints.comments.GetCommentsRequestImpl
import endpoints.events.EventsResponse
import endpoints.events.GetEventsRequestImpl
import endpoints.forum.CreateTopicRequestImpl
import endpoints.forum.CreateTopicResponse
import endpoints.forum.EditPostRequestImpl
import endpoints.forum.EditTopicRequestImpl
import endpoints.forum.ForumAndTopicsResponse
import endpoints.forum.ForumTopicAndPostsResponse
import endpoints.forum.ForumTopicResponse
import endpoints.forum.GetForumAndTopicsRequestImpl
import endpoints.forum.GetForumListingRequestImpl
import endpoints.forum.GetTopicAndPostRequestImpl
import endpoints.forum.GetTopicListingRequestImpl
import endpoints.forum.ReplyTopicRequestImpl
import endpoints.forum.TopicRequest
import endpoints.scores.GetScoresRequestImpl
import endpoints.scores.ScoreDownloadRequestImpl
import endpoints.scores.ScoreResponse
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
import models.CommentSort
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

    /**
     *  Get Beatmapset Discussion Posts
     *
     *  Returns the posts of beatmapset discussions.
     *
     *  @param beatmapsetDiscussionId id of the BeatmapsetDiscussion.
     *  @param limit Maximum number of results.
     *  @param page Search result page.
     *  @param sort for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.
     *  @param types first, reply, system are the valid values. Defaults to reply
     *  @param userId The id of the User.
     *  @param withDeleted This param has no effect as api calls do not currently receive group permissions.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-posts
     */
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

    /**
     *  Get Beatmapset Discussion Votes
     *
     *  Returns the votes given to beatmapset discussions.
     *
     *  @param beatmapsetDiscussionId id of the BeatmapsetDiscussion.
     *  @param limit Maximum number of results.
     *  @param page Search result page.
     *  @param receiver The id of the User receiving the votes.
     *  @param score 1 for up vote, -1 for down vote.
     *  @param sort for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.
     *  @param userId The id of the User.
     *  @param withDeleted This param has no effect as api calls do not currently receive group permissions.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-votes
     */
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

    /**
     *  Get Beatmapset Discussions
     *
     *  Returns a list of beatmapset discussions.
     *
     *  @param beatmapId id of the Beatmap.
     *  @param beatmapsetId id of the Beatmapset.
     *  @param beatmapsetStatus One of all, ranked, qualified, disqualified, never_qualified. Defaults to all. TODO: better descriptions.
     *  @param limit Maximum number of results.
     *  @param messageTypes suggestion, problem, mapper_note, praise, hype, review. Blank defaults to all types. TODO: better descriptions.
     *  @param onlyUnresolved true to show only unresolved issues; false, otherwise. Defaults to false.
     *  @param page Search result page.
     *  @param sort for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.
     *  @param userId The id of the User.
     *  @param withDeleted This param has no effect as api calls do not currently receive group permissions.
     *  @param cursorString CursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-beatmapset-discussions
     */
    suspend fun getBeatmapsetDiscussion(
        beatmapId: String? = null,
        beatmapsetId: String? = null,
        beatmapsetStatus: String? = null,
        limit: Int? = 100,
        messageTypes: List<String>? = null,
        onlyUnresolved: Boolean? = false,
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

    suspend fun searchBeatmapset(cursorString: String? = null): SearchBeatmapsetResponse {
        return SearchBeatmapsetRequestImpl(cursorString).request(client)
    }

    suspend fun getBeatmapset(beatmapsetId: Int): Beatmapset {
        return GetBeatmapsetRequestImpl(beatmapsetId).request(client)
    }

    suspend fun getBeatmapsetEvents(): BeatmapsetEventsResponse {
        return GetBeatmapsetEvents().request(client)
    }

    /**
     *  Get Changelog Build
     *
     *  Returns details of the specified build.
     *
     *  @param stream (Optional) Update stream name.
     *  @param build (Optional) Build version.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-changelog-build
     */
    suspend fun getChangelogBuild(stream: String, build: String): Build {
        return GetChangelogBuildRequestImpl(stream, build).request(client)
    }

    /**
     *  Get Changelog Listing
     *
     *  Returns a listing of update streams, builds, and changelog entries.
     *
     *  @param from (Optional) Minimum build version.
     *  @param maxId (Optional) Maximum build ID.
     *  @param stream (Optional) Stream name to return builds from.
     *  @param to (Optional) Maximum build version.
     *  @param messageFormats (Optional) html, markdown. Default to both.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-changelog-listing
     */
    suspend fun getChangelogListing(
        from: String? = null,
        maxId: Int? = null,
        stream: String? = null,
        to: String? = null,
        messageFormats: List<String>? = listOf("html, markdown")
    ): BuildResponse {
        return GetChangelogListingRequestImpl(from, maxId, stream, to, messageFormats).request(client)
    }

    /**
     *  Lookup Changelog Build
     *
     *  Returns details of the specified build.
     *
     *  @param build (Optional) Build version, update stream name, or build ID.
     *  @param messageFormats (Optional) html, markdown. Default to both.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#lookup-changelog-build
     */
    suspend fun lookupChangelogBuild(build: String, messageFormats: List<String>? = listOf("html, markdown")): Build {
        return LookupChangelogBuildRequestImpl(build, messageFormats).request(client)
    }

    /**
     * Download Score
     *
     * This method returns ByteArray from Score
     *
     * @param scoreId Id of the Score
     * @param isOldFormat If your score ID is in the old format (https://osu.ppy.sh/scores/osu/4459998279)
     * this param should be true, or if is in new format (https://osu.ppy.sh/scores/1695006824) you can
     * ignore this param. Defaults to false.
     *
     * implements endpoint: https://osu.ppy.sh/docs/index.html#get-apiv2scoresscoredownload
     */
    suspend fun downloadScore(scoreId: Long, isOldFormat: Boolean? = false): ByteArray {
        return ScoreDownloadRequestImpl(scoreId, isOldFormat).request(client)
    }

    /**
     *  Get Scores
     *
     *  Returns all passed scores. Up to 1000 scores will be returned in
     *  order of oldest to latest. Most recent scores will be returned if
     *  cursor_string parameter is not specified.
     *
     *  Obtaining new scores that arrived after the last request can be done
     *  by passing cursor_string parameter from the previous request.
     *
     *  @param mode (Optional) The Ruleset to get scores for.
     *  @param cursorString (Optional) Next set of scores
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-scores97
     */
    suspend fun getScore(mode: ModeEnum? = ModeEnum.OSU, cursorString: String? = null): ScoreResponse {
        return GetScoresRequestImpl(mode, cursorString).request(client)
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
    
    /**
     *  Get Comments
     *
     *  Returns a list of comments and their replies up to 2 levels deep.
     *
     *  @param after (Optional) Return comments after the specified comment id.
     *  @param commentableType (Optional) The type of resource to get comments for.
     *  @param commentableId (Optional) The id of the resource to get comments for.
     *  @param cursor (Optional) Pagination option. See CommentSort for detail.
     *  @param parentId (Optional) Limit to comments which are reply to the specified id. Specify 0 to get top level comments.
     *  @param sort (Optional) Sort option as defined in CommentSort. Defaults to NEW for guests and user-specified default when authenticated.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-comments
     */
    suspend fun getComments(
        after: Int? = null,
        commentableType: String? = null,
        commentableId: Int? = null,
        cursor: String? = null,
        parentId: Int? = null,
        sort: CommentSort? = CommentSort.NEW
    ): CommentBundle {
        return GetCommentsRequestImpl(after, commentableType, commentableId, cursor, parentId, sort).request(client)
    }
    
    /**
     *  Get Comment
     *
     *  Gets a comment and its replies up to 2 levels deep.
     *
     *  @param commentId The comment ID.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-comment
     */
    suspend fun getComment(commentId: Int): CommentBundle {
        return GetCommentRequestImpl(commentId).request(client)
    }

    /**
     *  Get Events
     *
     *  Returns a collection of Events in order of creation time.
     *
     *  @param sort (Optional) Sorting option. Valid values are id_desc (default) and id_asc.
     *  @param cursorString (Optional) CursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-events
     */
    suspend fun getEvents(
        sort: String? = "id_desc",
        cursorString: String? = null
    ): EventsResponse {
        return GetEventsRequestImpl(sort, cursorString).request(client)
    }

    /**
     *  Get Topic Listing
     *
     *  Get a sorted list of topics, optionally from a specific forum
     *
     *  @param forumId Id of a specific forum to get topics from.
     *  @param sort Topic sorting option. Valid values are new (default) and old. Both sort by the topic's last post time.
     *  @param limit Maximum number of topics to be returned (50 at most and by default).
     *  @param cursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-topic-listing
     */
    suspend fun getTopicListing(
        forumId: String? = null,
        sort: String? = "new",
        limit: Int? = 50,
        cursorString: String? = null
    ): ForumTopicResponse {
        return GetTopicListingRequestImpl(forumId, sort, limit, cursorString).request(client)
    }

    /**
     *  Get Topic and Posts
     *
     *  Get topic and its posts.
     *
     *  @param topicId Id of the topic.
     *  @param sort Post sorting option. Valid values are id_asc (default) and id_desc.
     *  @param limit Maximum number of posts to be returned (20 default, 50 at most).
     *  @param start First post id to be returned with sort set to id_asc. This parameter is ignored if cursor_string is specified.
     *  @param end First post id to be returned with sort set to id_desc. This parameter is ignored if cursor_string is specified.
     *  @param cursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-topic-and-posts
     */
    suspend fun getTopicAndPosts(
        topicId: Int,
        sort: String? = "id_asc",
        limit: Int? = 20,
        start: String? = null,
        end: String? = null,
        cursorString: String? = null,
    ): ForumTopicAndPostsResponse {
        return GetTopicAndPostRequestImpl(topicId, sort, limit, start, end, cursorString).request(client)
    }

    suspend fun getForumListing(): List<Forum> {
        return GetForumListingRequestImpl().request(client)
    }

    suspend fun getForumAndTopics(forumId: Int): ForumAndTopicsResponse {
        return GetForumAndTopicsRequestImpl(forumId).request(client)
    }

    suspend fun createTopic(topicRequest: TopicRequest): CreateTopicResponse {
        return CreateTopicRequestImpl(topicRequest).request(client)
    }

    suspend fun editTopic(topicId: Int, title: String): ForumTopic {
        return EditTopicRequestImpl(topicId, title).request(client)
    }

    suspend fun replyTopic(topicId: Int, body: String): ForumPost {
        return ReplyTopicRequestImpl(topicId, body).request(client)
    }

    suspend fun editPost(postId: Int, body: String): ForumPost {
        return EditPostRequestImpl(postId, body).request(client)
    }
}