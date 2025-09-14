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
import sdk.OsuSDK

@OptIn(ExperimentalSerializationApi::class)
class OsuKDK(private val client: HttpClient) : OsuSDK {

    companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            explicitNulls = true
            namingStrategy = JsonNamingStrategy.Builtins.SnakeCase
        }
    }

    override suspend fun getBeatmapPacks(type: BeatmapPackType?, cursor: String?) = GetBeatmapPacksRequestImpl(type, cursor).request(client)

    override suspend fun getBeatmapPack(pack: String, legacyOnly: Boolean?) = GetBeatmapPackRequestImpl(pack, legacyOnly).request(client)

    override suspend fun getUserBeatmapScore(
        beatmapId: Int,
        userId: Int,
        legacyOnly: Boolean?,
        mode: Ruleset?,
        mods: String?
    ) = GetUserBeatmapScoreRequestImpl(beatmapId, userId, legacyOnly, mode, mods).request(client)

    override suspend fun getUserBeatmapScores(beatmapId: Int, userId: Int, legacyOnly: Boolean?, mode: Ruleset?) = GetUserBeatmapScoresRequestImpl(beatmapId, userId, legacyOnly, mode).request(client)

    override suspend fun getBeatmapScores(
        beatmapId: Int,
        legacyOnly: Boolean?,
        mode: Ruleset?,
        mods: String?,
        type: String?
    ) = GetBeatmapScoresRequestImpl(beatmapId, legacyOnly, mode, mods, type).request(client)

    override suspend fun getBeatmaps(ids: List<Int>?) = GetBeatmapsRequestImpl(ids).request(client)

    override suspend fun getBeatmap(beatmapId: Int) = GetBeatmapRequestImpl(beatmapId).request(client)

    override suspend fun getBeatmapAttributes(beatmapId: Int, mods: List<ModLegacy>, mode: Ruleset) = GetBeatmapAttributesRequestImpl(beatmapId, mods, mode).request(client)

    override suspend fun getBeatmapsetDiscussionPosts(
        beatmapsetDiscussionId: String?,
        limit: Int?,
        page: Int?,
        sort: Sort?,
        types: List<BeatmapsetDiscussionPostTypes>?,
        userId: String?,
        withDeleted: String?
    ) = GetBeatmapsetDiscussionPostsRequestImpl(beatmapsetDiscussionId, limit, page, sort, types, userId, withDeleted).request(client)

    override suspend fun getBeatmapsetDiscussionVotes(
        beatmapsetDiscussionId: String?,
        limit: Int?,
        page: Int?,
        receiver: String?,
        score: String?,
        sort: Sort?,
        userId: String?,
        withDeleted: String?
    ) = GetBeatmapsetDiscussionVotesRequestImpl(beatmapsetDiscussionId, limit, page, receiver, score, sort, userId, withDeleted).request(client)

    override suspend fun getBeatmapsetDiscussion(
        beatmapId: String?,
        beatmapsetId: String?,
        beatmapsetStatus: String?,
        limit: Int?,
        messageTypes: List<String>?,
        onlyUnresolved: Boolean?,
        page: Int?,
        sort: Sort?,
        userId: String?,
        withDeleted: String?,
        cursorString: String?,
    ) = GetBeatmapsetDiscussionsRequestImpl(beatmapId, beatmapsetId, beatmapsetStatus, limit, messageTypes, onlyUnresolved, page, sort, userId, withDeleted, cursorString).request(client)

    override suspend fun searchBeatmapset(cursorString: String?) = SearchBeatmapsetRequestImpl(cursorString).request(client)

    override suspend fun getBeatmapset(beatmapsetId: Int) = GetBeatmapsetRequestImpl(beatmapsetId).request(client)

    override suspend fun getBeatmapsetEvents() = GetBeatmapsetEvents().request(client)

    override suspend fun getChangelogBuild(stream: String, build: String) = GetChangelogBuildRequestImpl(stream, build).request(client)

    override suspend fun getChangelogListing(
        from: String?,
        maxId: Int?,
        stream: String?,
        to: String?,
        messageFormats: List<String>?
    ) = GetChangelogListingRequestImpl(from, maxId, stream, to, messageFormats).request(client)

    override suspend fun lookupChangelogBuild(build: String, messageFormats: List<String>?) = LookupChangelogBuildRequestImpl(build, messageFormats).request(client)

    override suspend fun sendPM(targetId: Int, message: String, isAction: Boolean, uuid: String?) = CreateNewPMRequestImpl(targetId, message, isAction, uuid).request(client)

    override suspend fun getFriends() = GetFriendsRequestImpl().request(client)

    override suspend fun getChannelList() = GetChannelListRequestImpl().request(client)

    override suspend fun getChannel(channelId: Int) = GetChannelRequestImpl(channelId).request(client)

    override suspend fun getChannelMessages(channelId: Int, limit: Int?, since: Int?, until: Int?) = GetChannelMessagesRequestImpl(channelId, limit, since, until).request(client)

    override suspend fun sendMessageChannel(channelId: Int, message: String, isAction: Boolean) = SendMessageChannelRequestImpl(channelId, message, isAction).request(client)

    override suspend fun joinChannel(channelId: Int, userId: Int) = JoinChannelRequestImpl(channelId, userId).request(client)

    override suspend fun leaveChannel(channelId: Int, userId: Int) = LeaveChannelRequestImpl(channelId, userId).request(client)

    override suspend fun downloadScore(scoreId: Long, isOldFormat: Boolean?) = ScoreDownloadRequestImpl(scoreId, isOldFormat).request(client)

    override suspend fun getScore(mode: Ruleset?, cursorString: String?) = GetScoresRequestImpl(mode, cursorString).request(client)

    override suspend fun getUser(userId: Int, mode: Ruleset) = GetUserRequestImpl(userId, mode).request(client)

    override suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean?) = GetUsersRequestImpl(ids, includeVariantStatistics).request(client)

    override suspend fun getUserKudosu(userId: Int, limit: Int?, offset: String?) = GetUserKudosuRequestImpl(userId, limit, offset).request(client)

    override suspend fun getUserScore(
        userId: Int,
        type: ScoreType?,
        legacyOnly: Boolean?,
        includeFails: Boolean?,
        mode: Ruleset?,
        offset: Int?,
        limit: Int?
    ) = GetUserScoresRequestImpl(userId, type, legacyOnly, includeFails, mode, offset, limit).request(client)

    override suspend fun getUserBeatmaps(userId: Int, type: BeatmapPlaycountType, offset: Int?, limit: Int?) = GetUserBeatmapsRequestImpl(userId, type, offset, limit).request(client)

    override suspend fun getUserRecentActivity(userId: Int, offset: Int?, limit: Int?) = GetUserRecentActivityRequestImpl(userId, offset, limit).request(client)

    override suspend fun searchBeatmapsPassed(
        userId: Int,
        beatmapsetIds: List<Int>?,
        excludeConverts: Boolean?,
        isLegacy: Boolean?,
        noDiffReduction: Boolean?,
        rulesetId: Int?
    ) = SearchBeatmapsPassedRequestImpl(userId, beatmapsetIds, excludeConverts, isLegacy, noDiffReduction, rulesetId).request(client)

    override suspend fun getOwnData(mode: Ruleset?) = GetOwnDataRequestImpl(mode).request(client)
    
    override suspend fun getComments(
        after: Int?,
        commentableType: String?,
        commentableId: Int?,
        cursor: String?,
        parentId: Int?,
        sort: CommentSort?
    ) = GetCommentsRequestImpl(after, commentableType, commentableId, cursor, parentId, sort).request(client)
    
    override suspend fun getComment(commentId: Int) = GetCommentRequestImpl(commentId).request(client)

    override suspend fun getEvents(sort: String?, cursorString: String?) = GetEventsRequestImpl(sort, cursorString).request(client)

    override suspend fun getTopicListing(forumId: String?, sort: String?, limit: Int?, cursorString: String?) = GetTopicListingRequestImpl(forumId, sort, limit, cursorString).request(client)

    override suspend fun getTopicAndPosts(
        topicId: Int,
        sort: String?,
        limit: Int?,
        start: String?,
        end: String?,
        cursorString: String?,
    ) = GetTopicAndPostsRequestImpl(topicId, sort, limit, start, end, cursorString).request(client)

    override suspend fun getForumListing() = GetForumListingRequestImpl().request(client)

    override suspend fun getForumAndTopics(forumId: Int) = GetForumAndTopicsRequestImpl(forumId).request(client)

    override suspend fun createTopic(
        body: String,
        forumId: Int,
        title: String,
        withPoll: Boolean?,
        pollHideResults: Boolean?,
        pollLengthDays: Int?,
        pollMaxOptions: Int?,
        pollOptions: String?,
        pollTitle: String?,
        pollVoteChange: Boolean?
    ) = CreateTopicRequestImpl(body, forumId, title, withPoll, pollHideResults, pollLengthDays, pollMaxOptions, pollOptions, pollTitle, pollVoteChange).request(client)

    override suspend fun editTopic(topicId: Int, title: String) = EditTopicRequestImpl(topicId, title).request(client)

    override suspend fun replyTopic(topicId: Int, body: String) = ReplyTopicRequestImpl(topicId, body).request(client)

    override suspend fun editPost(postId: Int, body: String) = EditPostRequestImpl(postId, body).request(client)

    override suspend fun search(mode: String?, query: String?, page: Int?) = GetHomeSearchRequestImpl(mode, query, page).request(client)

    override suspend fun getMatchesListing(limit: Int?, sort: String?, cursorString: String?) = GetMatchesListingRequestImpl(limit, sort, cursorString).request(client)

    override suspend fun getMatch(matchId: Long, before: Int?, after: Int?, limit: Int?) = GetMatchRequestImpl(matchId, before, after, limit).request(client)

    override suspend fun getMultiplayerRooms(
        limit: Int?,
        mode: String?,
        seasonId: String?,
        sort: String?,
        typeGroup: String?
    ) = GetMultiplayerRoomsRequestImpl(limit, mode, seasonId, sort, typeGroup).request(client)

    override suspend fun getMultiplayerScores(
        roomId: Int,
        playlistId: Int,
        limit: Int?,
        sort: String?,
        cursorString: String?
    ) = GetMultiplayerScoresRequestImpl(roomId, playlistId, limit, sort, cursorString).request(client)

    override suspend fun getNewsListing(limit: Int?, year: Int?, cursorString: String?) = GetNewsListingRequestImpl(limit, year, cursorString).request(client)

    override suspend fun getNewsPost(slug: String, key: String?) = GetNewsPostRequestImpl(slug, key).request(client)

    override suspend fun revokeToken() = RevokeTokenRequestImpl().request(client)

    override suspend fun getKudosuRanking(page: Int?) = GetKudosuRankingRequestImpl(page).request(client)

    override suspend fun getRanking(
        rankingType: RankingType,
        mode: Ruleset,
        country: String?,
        cursor: Rankings.Cursor?,
        filter: String?,
        spotlight: String?,
        variant: String?
    ) = GetRankingRequestImpl(rankingType, mode, country, cursor, filter, spotlight, variant).request(client)

    override suspend fun getSpotlights() = GetSpotlightsRequestImpl().request(client)

    override suspend fun getWikiPage(locale: String, path: String) = GetWikiPageRequestImpl(locale, path).request(client)
}