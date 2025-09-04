import endpoints.requests.beatmap_packs.GetBeatmapPackRequestImpl
import endpoints.requests.beatmap_packs.GetBeatmapPacksRequestImpl
import endpoints.requests.beatmaps.*
import endpoints.requests.beatmapset_discussions.GetBeatmapsetDiscussionPostsRequestImpl
import endpoints.requests.beatmapset_discussions.GetBeatmapsetDiscussionVotesRequestImpl
import endpoints.requests.beatmapset_discussions.GetBeatmapsetDiscussionsRequestImpl
import endpoints.requests.beatmapsets.GetBeatmapsetEvents
import endpoints.requests.beatmapsets.GetBeatmapsetRequestImpl
import endpoints.requests.beatmapsets.SearchBeatmapsetRequestImpl
import endpoints.requests.changelog.GetChangelogBuildRequestImpl
import endpoints.requests.changelog.GetChangelogListingRequestImpl
import endpoints.requests.changelog.LookupChangelogBuildRequestImpl
import endpoints.requests.chat.CreateNewPMRequestImpl
import endpoints.requests.comments.GetCommentRequestImpl
import endpoints.requests.comments.GetCommentsRequestImpl
import endpoints.requests.events.GetEventsRequestImpl
import endpoints.requests.forum.*
import endpoints.requests.home.GetHomeSearchRequestImpl
import endpoints.requests.matches.GetMatchRequestImpl
import endpoints.requests.matches.GetMatchesListingRequestImpl
import endpoints.requests.multiplayer.GetMultiplayerRoomsRequestImpl
import endpoints.requests.multiplayer.GetMultiplayerScoresRequestImpl
import endpoints.requests.news.GetNewsListingRequestImpl
import endpoints.requests.news.GetNewsPostRequestImpl
import endpoints.requests.oauth_tokens.RevokeTokenRequestImpl
import endpoints.requests.ranking.GetKudosuRankingRequestImpl
import endpoints.requests.ranking.GetRankingRequestImpl
import endpoints.requests.ranking.GetSpotlightsRequestImpl
import endpoints.requests.scores.GetScoresRequestImpl
import endpoints.requests.scores.ScoreDownloadRequestImpl
import endpoints.requests.user.*
import endpoints.requests.wiki.GetWikiPageRequestImpl
import endpoints.responses.beatmap_pack.BeatmapPackResponse
import endpoints.responses.beatmaps.BeatmapDifficultyAttributesResponse
import endpoints.responses.beatmaps.BeatmapsResponse
import endpoints.responses.beatmaps.UserBeatmapsScoresResponse
import endpoints.responses.beatmapset_discussions.BeatmapsetDiscussionPostsResponse
import endpoints.responses.beatmapset_discussions.BeatmapsetDiscussionResponse
import endpoints.responses.beatmapset_discussions.BeatmapsetDiscussionVotesResponse
import endpoints.responses.beatmapsets.BeatmapsetEventsResponse
import endpoints.responses.beatmapsets.SearchBeatmapsetResponse
import endpoints.responses.changelog.BuildResponse
import endpoints.responses.chat.CreateNewPMResponse
import endpoints.requests.chat.GetChannelListRequestImpl
import endpoints.requests.chat.GetChannelMessagesRequestImpl
import endpoints.requests.chat.GetChannelRequestImpl
import endpoints.requests.chat.JoinChannelRequestImpl
import endpoints.requests.chat.LeaveChannelRequestImpl
import endpoints.requests.chat.SendMessageChannelRequestImpl
import endpoints.requests.friends.GetFriendsRequestImpl
import endpoints.responses.chat.GetChannelResponse
import endpoints.responses.events.EventsResponse
import endpoints.responses.forums.CreateTopicResponse
import endpoints.responses.forums.ForumAndTopicsResponse
import endpoints.responses.forums.ForumTopicAndPostsResponse
import endpoints.responses.forums.ForumTopicResponse
import endpoints.responses.matches.MatchResponse
import endpoints.responses.matches.MatchesResponse
import endpoints.responses.news.NewsListingResponse
import endpoints.responses.rankings.KudosuRankingResponse
import endpoints.responses.rankings.SpotlightsRankingResponse
import endpoints.responses.scores.ScoreResponse
import endpoints.responses.users.SearchBeatmapsPassedResponse
import endpoints.responses.users.UsersResponse
import enums.*
import events.impl.Event
import io.ktor.client.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import models.beatmaps.*
import models.changelog.Build
import models.chat.ChatChannel
import models.chat.ChatMessage
import models.comments.CommentBundle
import models.forums.Forum
import models.forums.ForumPost
import models.forums.ForumTopic
import models.home.Search
import models.multiplayer.MultiplayerScores
import models.multiplayer.Room
import models.news.NewsPost
import models.rankings.Rankings
import models.scores.Score
import models.users.KudosuHistory
import models.users.User
import models.users.UserRelation
import models.wiki.WikiPage

@OptIn(ExperimentalSerializationApi::class)
class OsuKDK(private val client: HttpClient) {

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
    suspend fun getBeatmapPacks(type: BeatmapPackType? = BeatmapPackType.STANDARD, cursor: String? = ""): BeatmapPackResponse {
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
        mode: Ruleset? = Ruleset.OSU,
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
        mode: Ruleset? = Ruleset.OSU
    ): UserBeatmapsScoresResponse {
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
        mode: Ruleset? = Ruleset.OSU,
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
    suspend fun getBeatmaps(ids: List<Int>? = listOf()): BeatmapsResponse {
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
    suspend fun getBeatmapAttributes(beatmapId: Int, mods: List<ModLegacy>, mode: Ruleset): BeatmapDifficultyAttributesResponse {
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
        sort: Sort? = Sort.NEWEST,
        types: List<BeatmapsetDiscussionPostTypes>? = listOf(BeatmapsetDiscussionPostTypes.REPLY),
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
        sort: Sort? = Sort.NEWEST,
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
        sort: Sort? = Sort.NEWEST,
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
     *  Create New PM (Require CHAT_WRITE scope)
     *
     *  This endpoint allows you to create a new PM channel.
     *
     *  @param targetId user_id of user to start PM with
     *  @param message message to send
     *  @param isAction (Optional) whether the message is an action. Defaults to false
     *  @param uuid (Optional) client-side message identifier which will be sent back in response and websocket json.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#create-new-pm
     */
    suspend fun sendPM(
        targetId: Int, message: String, isAction: Boolean = false, uuid: String? = null
    ): CreateNewPMResponse {
        return CreateNewPMRequestImpl(targetId, message, isAction, uuid).request(client)
    }

    /**
     * Get Friends list (Require FRIENDS_READ scope)
     *
     * This endpoint return all of your friend in a list
     *
     * if your api version is less than 20241022 the attributes
     * `relationType` and `mutual` will always be null.
     *
     * implements the endpoint: https://osu.ppy.sh/docs/index.html#get-apiv2friends
     */
    suspend fun getFriends(): List<UserRelation> {
        return GetFriendsRequestImpl().request(client)
    }

    /**
     *  Get Channel List (Require CHAT_READ scope)
     *
     *  This endpoint returns a list of all joinable public channels.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-channel-list
     */
    suspend fun getChannelList(): List<ChatChannel> {
        return GetChannelListRequestImpl().request(client)
    }

    /**
     *  Get Channel (Require CHAT_READ scope)
     *
     *  Gets details of a chat channel.
     *
     *  @param channelId ID of the channel.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-channel
     */
    suspend fun getChannel(channelId: Int): GetChannelResponse {
        return GetChannelRequestImpl(channelId).request(client)
    }

    /**
     *  Get Channel Messages (Require CHAT_READ scope)
     *
     *  This endpoint returns the chat messages for a specific channel.
     *
     *  @param channelId The ID of the channel to retrieve messages for
     *  @param limit number of messages to return (max of 50)
     *  @param since messages after the specified message id will be returned
     *  @param until messages up to but not including the specified message id will be returned
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-channel-messages
     */
    suspend fun getChannelMessages(
        channelId: Int, limit: Int? = 50, since: Int? = null, until: Int? = null
    ): List<ChatMessage> {
        return GetChannelMessagesRequestImpl(channelId, limit, since, until).request(client)
    }

    /**
     *  Send Message to Channel (Require CHAT_WRITE scope)
     *
     *  This endpoint returns the chat messages for a specific channel.
     *
     *  @param channelId The channel_id of the channel to send message to
     *  @param message message to send
     *  @param isAction whether the message is an action
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#send-message-to-channel
     */
    suspend fun sendMessageChannel(channelId: Int, message: String, isAction: Boolean = false): ChatMessage {
        return SendMessageChannelRequestImpl(channelId, message, isAction).request(client)
    }

    /**
     *  Join Channel (Require CHAT_WRITE_MANAGE scope)
     *
     *  This endpoint allows you to join a public or multiplayer channel.
     *
     *  @param channelId The channel_id of the channel
     *  @param userId The user_id user.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#join-channel
     */
    suspend fun joinChannel(channelId: Int, userId: Int): ChatChannel {
        return JoinChannelRequestImpl(channelId, userId).request(client)
    }

    /**
     *  Join Channel (Require CHAT_WRITE_MANAGE scope)
     *
     *  This endpoint allows you to leave a public channel.
     *
     *  @param channelId The channel_id of the channel
     *  @param userId The user_id user.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#leave-channel
     */
    suspend fun leaveChannel(channelId: Int, userId: Int) {
        return LeaveChannelRequestImpl(channelId, userId).request(client)
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
    suspend fun getScore(mode: Ruleset? = Ruleset.OSU, cursorString: String? = null): ScoreResponse {
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
    suspend fun getUser(userId: Int, mode: Ruleset = Ruleset.OSU): User {
        return GetUserRequestImpl(userId, mode).request(client)
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
    suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean? = true): UsersResponse {
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
        type: ScoreType? = ScoreType.RECENT,
        legacyOnly: Boolean? = false,
        includeFails: Boolean? = false,
        mode: Ruleset? = Ruleset.OSU,
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
        type: BeatmapPlaycountType,
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
    ): SearchBeatmapsPassedResponse {
        return SearchBeatmapsPassedRequestImpl(
            userId,
            beatmapsetIds,
            excludeConverts,
            isLegacy,
            noDiffReduction,
            rulesetId
        ).request(client)
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
    suspend fun getOwnData(mode: Ruleset? = Ruleset.OSU): User {
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
        return GetTopicAndPostsRequestImpl(topicId, sort, limit, start, end, cursorString).request(client)
    }

    /**
     *  Get Forum Listing
     *
     *  Get top-level forums and their subforums (max 2 deep).

     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-forum-listing
     */
    suspend fun getForumListing(): List<Forum> {
        return GetForumListingRequestImpl().request(client)
    }

    /**
     *  Get Forum and Topics
     *
     *  Get a forum by id, its pinned topics, recent topics, and its subforums.
     *
     *  @param forumId Id of the forum.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-forum-and-topics
     */
    suspend fun getForumAndTopics(forumId: Int): ForumAndTopicsResponse {
        return GetForumAndTopicsRequestImpl(forumId).request(client)
    }

    /**
     *  Create Topic
     *
     *  Create a new topic.
     *
     *  @param body Content of the topic.
     *  @param forumId Forum to create the topic in.
     *  @param title Title of the topic.
     *  @param withPoll Enable this to also create poll in the topic (default: false).
     *  @param pollHideResults Enable this to hide result until voting period ends (default: false).
     *  @param pollLengthDays Number of days for voting period. 0 means the voting will never ends (default: 0). This parameter is required if hide_results option is enabled.
     *  @param pollMaxOptions Maximum number of votes each user can cast (default: 1).
     *  @param pollOptions Newline-separated list of voting options. BBCode is supported.
     *  @param pollTitle Title of the poll.
     *  @param pollVoteChange Enable this to allow user to change their votes (default: false).
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#create-topic
     */
    suspend fun createTopic(
        body: String,
        forumId: Int,
        title: String,
        withPoll: Boolean? = false,
        pollHideResults: Boolean? = false,
        pollLengthDays: Int? = 0,
        pollMaxOptions: Int? = 1,
        pollOptions: String? = null,
        pollTitle: String? = null,
        pollVoteChange: Boolean? = false
    ): CreateTopicResponse {
        return CreateTopicRequestImpl(body, forumId, title, withPoll, pollHideResults, pollLengthDays, pollMaxOptions, pollOptions, pollTitle, pollVoteChange).request(client)
    }

    /**
     *  Edit Topic
     *
     *  Edit topic. Only title can be edited through this endpoint.
     *
     *  @param topicId Id of the topic.
     *  @param title New topic title.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#edit-topic
     */
    suspend fun editTopic(topicId: Int, title: String): ForumTopic {
        return EditTopicRequestImpl(topicId, title).request(client)
    }

    /**
     *  Reply Topic
     *
     *  Create a post replying to the specified topic.
     *
     *  @param topicId Id of the topic.
     *  @param body Content of the reply post.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#reply-topic
     */
    suspend fun replyTopic(topicId: Int, body: String): ForumPost {
        return ReplyTopicRequestImpl(topicId, body).request(client)
    }

    /**
     *  Edit Post
     *
     *  Edit specified forum post.
     *
     *  @param postId Id of the post.
     *  @param body New post content in BBCode format.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#edit-post
     */
    suspend fun editPost(postId: Int, body: String): ForumPost {
        return EditPostRequestImpl(postId, body).request(client)
    }

    /**
     *  Search
     *
     *  Searches users and wiki pages.
     *
     *  @param mode (Optional) Either all, user, or wiki_page. Default is all.
     *  @param query (Optional) Search keyword.
     *  @param page Search result page. Ignored for mode all.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#search
     */
    suspend fun search(
        mode: String? = "all", query: String? = null, page: Int? = null,
    ): Search {
        return GetHomeSearchRequestImpl(mode, query, page).request(client)
    }

    /**
     *  Get Matches Listing
     *
     *  Returns a list of matches.
     *
     *  @param limit (Optional) Maximum number of matches (50 default, 1 minimum, 50 maximum).
     *  @param sort (Optional) id_desc for newest first; id_asc for oldest first. Defaults to id_desc.
     *  @param cursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-matches-listing
     */
    suspend fun getMatchesListing(
        limit: Int? = 50, sort: String? = "id_desc", cursorString: String? = null
    ): MatchesResponse {
        return GetMatchesListingRequestImpl(limit, sort, cursorString).request(client)
    }

    /**
     *  Get Match
     *
     *  Returns details of the specified match.
     *
     *  @param matchId Match ID.
     *  @param before (Optional) Filter for match events before the specified MatchEvent.id.
     *  @param after (Optional) Filter for match events after the specified MatchEvent.id.
     *  @param limit (Optional) Maximum number of match events (100 default, 1 minimum, 101 maximum).
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-match
     */
    suspend fun getMatch(
        matchId: Long,
        before: Int? = null,
        after: Int? = null,
        limit: Int? = 100
    ): MatchResponse {
        return GetMatchRequestImpl(matchId, before, after, limit).request(client)
    }

    /**
     *  Get Multiplayer Rooms
     *
     *  Returns a list of multiplayer rooms.
     *
     *  @param limit (Optional) Maximum number of results.
     *  @param mode (Optional) Filter mode; active (default), all, ended, participated, owned.
     *  @param seasonId (Optional) Season ID to return Rooms from.
     *  @param sort (Optional) Sort order; ended, created.
     *  @param limit (Optional) playlists (default) or realtime.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-match
     */
    suspend fun getMultiplayerRooms(
        limit: Int? = null,
        mode: String? = "active",
        seasonId: String? = null,
        sort: String? = null,
        typeGroup: String? = "playlists"
    ): List<Room> {
        return GetMultiplayerRoomsRequestImpl(limit, mode, seasonId, sort, typeGroup).request(client)
    }

    /**
     *  Get Scores
     *
     *  Returns a list of scores for specified playlist item.
     *
     *  @param roomId Id of the room.
     *  @param playlistId Id of the playlist item
     *  @param limit (Optional) Number of scores to be returned.
     *  @param sort (Optional) score_asc or score_desc.
     *  @param cursorString (Optional) for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-scores
     */
    suspend fun getMultiplayerScores(
        roomId: Int,
        playlistId: Int,
        limit: Int? = null,
        sort: String? = null,
        cursorString: String? = null
    ): MultiplayerScores {
        return GetMultiplayerScoresRequestImpl(roomId, playlistId, limit, sort, cursorString).request(client)
    }

    /**
     *  Get News Listing
     *
     *  Returns a list of news posts and related metadata.
     *
     *  @param limit (Optional) Maximum number of posts (12 default, 1 minimum, 21 maximum).
     *  @param year Year to return posts from.
     *  @param cursorString for pagination.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-news-listing
     */
    suspend fun getNewsListing(
        limit: Int? = 12, year: Int? = null, cursorString: String? = null
    ): NewsListingResponse {
        return GetNewsListingRequestImpl(limit, year, cursorString).request(client)
    }

    /**
     *  Get News Post
     *
     *  Returns details of the specified news post.
     *
     *  @param slug News post slug or ID.
     *  @param key Unset to query by slug, or id to query by ID.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-news-post
     */
    suspend fun getNewsPost(
        slug: String, key: String? = null
    ): NewsPost {
        return GetNewsPostRequestImpl(slug, key).request(client)
    }

    /**
     *  Revoke current token
     *
     *  Revokes currently authenticated token.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#revoke-current-token
     */
    suspend fun revokeToken() {
        return RevokeTokenRequestImpl().request(client)
    }

    /**
     *  Get Kudosu Ranking
     *
     *  Gets the kudosu ranking.
     *
     *  @param page (Optional) Ranking page.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-kudosu-ranking
     */
    suspend fun getKudosuRanking(page: Int? = null): KudosuRankingResponse {
        return GetKudosuRankingRequestImpl(page).request(client)
    }

    /**
     *  Get Ranking
     *
     *  Gets the current ranking for the specified type and game mode.
     *
     *  @param rankingType
     *  @param mode
     *  @param country (Optional) Filter ranking by country code. Only available for type of global.
     *  @param cursor (Optional) Cursor
     *  @param filter (Optional) Either all (default) or friends.
     *  @param spotlight (Optional) The id of the spotlight if type is charts. Ranking for latest spotlight will be returned if not specified.
     *  @param variant (Optional) Filter ranking to specified mode variant. For ruleset of mania, it's either 4k or 7k. Only available for type of global.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-ranking
     */
    suspend fun getRanking(
        rankingType: RankingType,
        mode: Ruleset,
        country: String? = null,
        cursor: Rankings.Cursor? = null,
        filter: String? = "all",
        spotlight: String? = null,
        variant: String? = null
    ): Rankings {
        return GetRankingRequestImpl(rankingType, mode, country, cursor, filter, spotlight, variant).request(client)
    }

    /**
     *  Get Spotlights
     *
     *  Gets the list of spotlights.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-spotlights
     */
    suspend fun getSpotlights(): SpotlightsRankingResponse {
        return GetSpotlightsRequestImpl().request(client)
    }

    /**
     *  Get Wiki Page
     *
     *  The wiki article or image data.
     *
     *  @param locale Two-letter language code of the wiki page.
     *  @param path The path name of the wiki page.
     *
     *  implements endpoint: https://osu.ppy.sh/docs/index.html#get-wiki-page
     */
    suspend fun getWikiPage(locale: String, path: String): WikiPage {
        return GetWikiPageRequestImpl(locale, path).request(client)
    }
}