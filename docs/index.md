# OsuKDK
---

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.vinnih-1:osukdk:0.2.0-alpha")
```

### Maven

```xml
<dependency>
    <groupId>io.github.vinnih-1</groupId>
    <artifactId>osukdk</artifactId>
    <version>0.2.0-ALPHA</version>
</dependency>
```

## Creating a Client

### Client Credentials Grant

```kotlin
val api = Authorization(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314)
println(user.username)
```

### Authorization Code Grant

```kotlin
val grantType = GrantType.AUTHORIZATION_CODE
val redirectUri = "" // This must match the registered Application Callback URL exactly.

val api = Authorization(
    YOUR_CLIENT_ID, YOUR_CLIENT_SECRET, grantType = grantType, redirectUri = redirectUri).create()
```

## Endpoints

### Beatmap Packs

```kotlin
suspend fun getBeatmapPack(pack, legacyOnly): BeatmapPackResponse 
```
#### Get Beatmap Pack

Gets the beatmap pack for the specified beatmap pack tag.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-pack](https://osu.ppy.sh/docs/index.html#get-beatmap-pack)

| Attribute  | Description                                                                                     |
|------------|-------------------------------------------------------------------------------------------------|
| pack       | The tag of the beatmap pack to be returned.                                                     |
| legacyOnly | (Optional) Whether or not to consider lazer scores for user completion data. Defaults to false. |

```kotlin
suspend fun getBeatmapPacks(type, cursor): BeatmapPackResponse
```
#### Get Beatmap Packs

Returns a list of beatmap packs.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-packs](https://osu.ppy.sh/docs/index.html#get-beatmap-packs)

| Attribute | Description                                                      |
|-----------|------------------------------------------------------------------|
| type      | (Optional) of the beatmap packs to be returned. Defaults to standard. |
| cursor    | (Optional) for pagination.                                       |

### Beatmap Scores

```kotlin
suspend fun getUserBeatmapScore(beatmapId, userId, legacyOnly, mode, mods): BeatmapUserScore
```
#### Get a User Beatmap score

Return a User's score on a Beatmap

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-score](https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-score)

| Attribute  | Description                                                                |
|------------|----------------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                         |
| userId     | Id of the User.                                                            |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false.      |
| mode       | (Optional) The Ruleset to get scores for.                                  |
| mods       | (Optional) An array of matching Mods, or none.                             |

```kotlin
suspend fun getUserBeatmapScores(beatmapId, userId, legacyOnly, mode): List<Score>
```
#### Get a User Beatmap scores

Return a User's scores on a Beatmap

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-scores](https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-scores)

| Attribute  | Description                                                           |
|------------|-----------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                    |
| userId     | Id of the User.                                                       |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false. |
| mode       | (Optional) The Ruleset to get scores for.                             |

```kotlin
suspend fun getBeatmapScores(beatmapId, legacyOnly, mode, mods, type): BeatmapScores
```
#### Get Beatmap scores

Returns the top scores for a beatmap. Depending on user preferences, this may only show legacy scores.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-scores](https://osu.ppy.sh/docs/index.html#get-beatmap-scores)

| Attribute  | Description                                                           |
|------------|-----------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                    |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false. |
| mode       | (Optional) The Ruleset to get scores for.                             |
| mods       | (Optional) An array of matching Mods, or none.                        |
| type       | (Optional) Beatmap score ranking type.                                |

### Beatmaps

```kotlin
suspend fun getBeatmaps(ids): List<Beatmap>
```
#### Get Beatmaps

Returns a list of beatmaps.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmaps](https://osu.ppy.sh/docs/index.html#get-beatmaps)

| Attribute | Description                                                                                                |
|-----------|------------------------------------------------------------------------------------------------------------|  
| ids       | Beatmap IDs to be returned. Specify once for each beatmap ID requested. Up to 50 beatmaps can be requested at once. |

```kotlin
suspend fun getBeatmap(beatmapId): Beatmap
```
#### Get Beatmap

Gets beatmap data for the specified beatmap ID.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap](https://osu.ppy.sh/docs/index.html#get-beatmap)

| Attribute | Description                |
|-----------|----------------------------|
| beatmapId | The ID of the beatmap.     |

```kotlin
suspend fun getBeatmapAttributes(beatmapId, mods, mode): BeatmapDifficultyAttributes
```
#### Get Beatmap Attributes

Returns difficulty attributes of beatmap with specific mode and mods combination.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-attributes](https://osu.ppy.sh/docs/index.html#get-beatmap-attributes)

| Attribute | Description                                                                                                                                                                                                |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| beatmapId | The ID of the beatmap.                                                                                                                                                                                     |
| mods      | array of mods                                                                                                                                                                                              |
| mode      | Ruleset of the difficulty attributes. Only valid if it's the beatmap ruleset or the beatmap can be converted to the specified ruleset. Defaults to ruleset of the specified beatmap.                        |

### Beatmapset Discussions

```kotlin
suspend fun getBeatmapsetDiscussionPosts(beatmapsetDiscussionId, limit, page, sort, types, userId, withDeleted): BeatmapsetDiscussionPostsResponse
```
#### Get Beatmapset Discussion Posts

Returns the posts of beatmapset discussions.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-posts](https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-posts)

| Attribute              | Description                                                                                                |
|------------------------|------------------------------------------------------------------------------------------------------------|  
| beatmapsetDiscussionId | id of the BeatmapsetDiscussion.                                                                            |
| limit                  | Maximum number of results.                                                                                  |
| page                   | Search result page.                                                                                         |
| sort                   | for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.                                              |
| types                  | first, reply, system are the valid values. Defaults to reply                                                |
| userId                 | The id of the User.                                                                                         |
| withDeleted            | This param has no effect as api calls do not currently receive group permissions.                           |

```kotlin
suspend fun getBeatmapsetDiscussionVotes(beatmapsetDiscussionId, limit, page, receiver, score, sort, userId, withDeleted): BeatmapsetDiscussionVotesResponse
```
#### Get Beatmapset Discussion Votes

Returns the votes given to beatmapset discussions.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-votes](https://osu.ppy.sh/docs/index.html#get-beatmapset-discussion-votes)

| Attribute              | Description                                                                                                |
|------------------------|------------------------------------------------------------------------------------------------------------|  
| beatmapsetDiscussionId | id of the BeatmapsetDiscussion.                                                                            |
| limit                  | Maximum number of results.                                                                                  |
| page                   | Search result page.                                                                                         |
| receiver               | The id of the User receiving the votes.                                                                     |
| score                  | 1 for up vote, -1 for down vote.                                                                            |
| sort                   | for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.                                              |
| userId                 | The id of the User.                                                                                         |
| withDeleted            | This param has no effect as api calls do not currently receive group permissions.                           |

```kotlin
suspend fun getBeatmapsetDiscussion(beatmapId, beatmapsetId, beatmapsetStatus, limit, messageTypes, onlyUnresolved, page, sort, userId, withDeleted, cursorString): BeatmapsetDiscussionResponse
```
#### Get Beatmapset Discussions

Returns a list of beatmapset discussions.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmapset-discussions](https://osu.ppy.sh/docs/index.html#get-beatmapset-discussions)

| Attribute        | Description                                                                                                |
|-----------------|------------------------------------------------------------------------------------------------------------|  
| beatmapId        | id of the Beatmap.                                                                                         |
| beatmapsetId     | id of the Beatmapset.                                                                                      |
| beatmapsetStatus | One of all, ranked, qualified, disqualified, never_qualified. Defaults to all.                            |
| limit            | Maximum number of results.                                                                                 |
| messageTypes     | suggestion, problem, mapper_note, praise, hype, review. Blank defaults to all types.                       |
| onlyUnresolved   | true to show only unresolved issues; false, otherwise. Defaults to false.                                  |
| page             | Search result page.                                                                                         |
| sort             | for NEWEST first; id_asc for OLDEST first. Defaults to NEWEST.                                             |
| userId           | The id of the User.                                                                                        |
| withDeleted      | This param has no effect as api calls do not currently receive group permissions.                          |
| cursorString     | CursorString for pagination.                                                                               |

### Beatmapsets

```kotlin
suspend fun searchBeatmapset(cursorString): SearchBeatmapsetResponse
```
#### Search Beatmapset

TODO: DOCS

| Attribute    | Description                |
|--------------|----------------------------|
| cursorString | CursorString for pagination. |

```kotlin
suspend fun getBeatmapset(beatmapsetId): Beatmapset
```
#### Get Beatmapset

TODO: DOCS

| Attribute    | Description                |
|--------------|----------------------------|
| beatmapsetId | The ID of the beatmapset.  |

```kotlin
suspend fun getBeatmapsetEvents(): BeatmapsetEventsResponse
```
#### Get Beatmapset Events

TODO: DOCS

No parameters.

### Changelog

```kotlin
suspend fun getChangelogBuild(stream, build): Build
```
#### Get Changelog Build

Returns details of the specified build.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-changelog-build](https://osu.ppy.sh/docs/index.html#get-changelog-build)

| Attribute | Description                |
|-----------|----------------------------|
| stream    | (Optional) Update stream name. |
| build     | (Optional) Build version.  |

```kotlin
suspend fun lookupChangelogBuild(build, messageFormats): Build
```
#### Lookup Changelog Build

Returns details of the specified build.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#lookup-changelog-build](https://osu.ppy.sh/docs/index.html#lookup-changelog-build)

| Attribute      | Description                                                |
|----------------|------------------------------------------------------------|  
| build          | (Optional) Build version, update stream name, or build ID. |
| messageFormats | (Optional) html, markdown. Default to both.                |

### Scores

```kotlin
suspend fun downloadScore(mode, cursorString): ScoreResponse
```
#### Download Score

This method returns ByteArray from Score

Implements endpoint: https://osu.ppy.sh/docs/index.html#get-apiv2scoresscoredownload

| Attribute   | Description                                                                                                                                                                                                             |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| scoreId     | Id of the Score                                                                                                                                                                                                         |
| isOldFormat | If your score ID is in the old format (https://osu.ppy.sh/scores/osu/4459998279) this param should be true, or if is in new format (https://osu.ppy.sh/scores/1695006824) you can ignore this param. Defaults to false. |

```kotlin
suspend fun getScore(mode, cursorString): ScoreResponse
```
#### Get Scores

Returns all passed scores. Up to 1000 scores will be returned in order of oldest to latest. Most recent scores will be returned if cursor_string parameter is not specified.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-scores97](https://osu.ppy.sh/docs/index.html#get-scores97)

| Attribute    | Description                                |
|--------------|--------------------------------------------|
| mode         | (Optional) The Ruleset to get scores for.  |
| cursorString | (Optional) Next set of scores              |

### Users

```kotlin
suspend fun getUser(userId, mode): User
```
#### Get User

This endpoint returns the detail of specified user.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user](https://osu.ppy.sh/docs/index.html#get-user)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| mode      | (Optional) osu mode will be used if not specified.          |

```kotlin
suspend fun getUsers(ids, includeVariantStatistics): List<User>
```
#### Get Users

Returns list of users.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-users](https://osu.ppy.sh/docs/index.html#get-users)

| Attribute                | Description                                                                                                |
|--------------------------|------------------------------------------------------------------------------------------------------------|
| ids                      | User id to be returned. Specify once for each user id requested. Up to 50 users can be requested at once.   |
| includeVariantStatistics | (Optional) Whether to additionally include statistics variants (default: true).                            |

```kotlin
suspend fun getUserKudosu(userId, limit, offset): List<KudosuHistory>
```
#### Get User Kudosu

Returns kudosu history

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-kudosu](https://osu.ppy.sh/docs/index.html#get-user-kudosu)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| limit     | (Optional) Maximum number of results. defaults to 50        |
| offset    | (Optional) Result offset for pagination. defaults to 0      |

```kotlin
suspend fun getUserScore(userId, type, legacyOnly, includeFails, mode, offset, limit): List<Score>
```
#### Get User Scores

This method returns the scores of specified user.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-scores](https://osu.ppy.sh/docs/index.html#get-user-scores)

| Attribute    | Description                                                                                                |
|--------------|------------------------------------------------------------------------------------------------------------|
| userId       | Id of the user.                                                                                             |
| type         | (Optional) Score type.                                                                                      |
| legacyOnly   | (Optional) Whether or not to exclude lazer scores. Defaults to false                                        |
| includeFails | (Optional) Only for recent scores, include scores of failed plays. Set to true to include them. Defaults to false. |
| mode         | (Optional) Mode of the scores to be returned. Defaults to osu mode.                                          |
| offset       | (Optional) Result offset for pagination. default is 0                                                        |
| limit        | (Optional) Maximum number of results. default is 100                                                         |

```kotlin
suspend fun getUserRecentActivity(userId, offset, limit): List<Event>
```
#### Get User Recent Activity

Returns recent activity.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-recent-activity](https://osu.ppy.sh/docs/index.html#get-user-recent-activity)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| offset    | (Optional) Result offset for pagination. default is 0       |
| limit     | (Optional) Maximum number of results. default is 100        |

```kotlin
suspend fun searchBeatmapsPassed(userId, beatmapsetIds, excludeConverts, isLegacy, noDiffReduction, rulesetId): List<Beatmap>
```
#### Search Beatmaps Passed

Searches for the Beatmaps a User has passed by Beatmapset.

Implements endpoint: https://osu.ppy.sh/docs/index.html#search-beatmaps-passed

| Attribute        | Description                                                                                                |
|------------------|------------------------------------------------------------------------------------------------------------|
| userId           | Id of the user.                                                                                             |
| beatmapsetIds    | (Optional) The list of Beatmapset.                                                                          |
| excludeConverts  | (Optional) Whether or not to exclude converts.                                                              |
| isLegacy         | (Optional) Whether or not to consider legacy scores. Leave empty for all scores.                            |
| noDiffReduction  | (Optional) Whether or not to exclude diff reduction mods. Defaults to true.                                 |
| rulesetId        | (Optional) The Ruleset ID. Leave empty for all rulesets.                                                    |

```kotlin
suspend fun getOwnData(mode): User
```
#### Get Own Data

Similar to Get User but with authenticated user (token owner) as user id.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-own-data](https://osu.ppy.sh/docs/index.html#get-own-data)

| Attribute | Description                                                |
|-----------|------------------------------------------------------------|
| mode      | (Optional) osu mode will be used if not specified.         |

### Comments

```kotlin
suspend fun getComments(after, commentableType, commentableId, cursor, parentId, sort): CommentBundle
```
#### Get Comments

Returns a list of comments and their replies up to 2 levels deep.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-comments](https://osu.ppy.sh/docs/index.html#get-comments)

| Attribute       | Description                                                                                                |
|-----------------|------------------------------------------------------------------------------------------------------------|
| after           | (Optional) Return comments after the specified comment id.                                                  |
| commentableType | (Optional) The type of resource to get comments for.                                                        |
| commentableId   | (Optional) The id of the resource to get comments for.                                                      |
| cursor          | (Optional) Pagination option. See CommentSort for detail.                                                   |
| parentId        | (Optional) Limit to comments which are reply to the specified id. Specify 0 to get top level comments.      |
| sort            | (Optional) Sort option as defined in CommentSort. Defaults to NEW for guests and user-specified default when authenticated. |

```kotlin
suspend fun getComment(commentId): CommentBundle
```
#### Get Comment

Gets a comment and its replies up to 2 levels deep.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-comment](https://osu.ppy.sh/docs/index.html#get-comment)

| Attribute | Description                |
|-----------|----------------------------|
| commentId | The comment ID.            |

### Events

```kotlin
suspend fun getEvents(sort, cursorString): EventsResponse
```
#### Get Events

Returns a collection of Events in order of creation time.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-events](https://osu.ppy.sh/docs/index.html#get-events)

| Attribute    | Description                                                                |
|--------------|----------------------------------------------------------------------------|
| sort         | (Optional) Sorting option. Valid values are id_desc (default) and id_asc. |
| cursorString | (Optional) CursorString for pagination.                                   |

### Forums

```kotlin
suspend fun replyTopic(topicId: Int, body: String): ForumPost
```
#### Reply Topic

Create a post replying to the specified topic.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#reply-topic](https://osu.ppy.sh/docs/index.html#reply-topic)

| Attribute | Description                       |
|-----------|-----------------------------------|
| topicId   | Id of the topic to be replied to. |
| body      | Content of the reply post.        |

```kotlin
suspend fun getTopicListing(forumId: String? = null, sort: String? = "new", limit: Int? = 50, cursorString: String? = null): ForumTopicResponse
```
#### Get Topic Listing

Get a sorted list of topics, optionally from a specific forum

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-topic-listing](https://osu.ppy.sh/docs/index.html#get-topic-listing)

| Attribute    | Description                                                                                            |
|--------------|--------------------------------------------------------------------------------------------------------|
| forumId      | Id of a specific forum to get topics from.                                                             |
| sort         | Topic sorting option. Valid values are new (default) and old. Both sort by the topic's last post time. |
| limit        | Maximum number of topics to be returned (50 at most and by default).                                   |
| cursorString | for pagination.                                                                                        |

```kotlin
suspend fun createTopic(topicRequest: TopicRequest): CreateTopicResponse
```
#### Create Topic

Create a new topic.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#create-topic](https://osu.ppy.sh/docs/index.html#create-topic)

| Attribute    | Description          |
|--------------|----------------------|
| topicRequest | the topic attributes |

```kotlin
suspend fun getTopicAndPosts(topicId, sort, limit, start, end, cursorString): ForumTopicAndPostsResponse
```
#### Get Topic and Posts

Get topic and its posts.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-topic-and-posts](https://osu.ppy.sh/docs/index.html#get-topic-and-posts)

| Attribute    | Description                                                                                                                              |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------|
| topicId      | Id of the topic.                                                                                                                         |
| sort         | (Optional) Post sorting option. Valid values are id_asc (default) and id_desc.                                                           |
| limit        | (Optional) Maximum number of posts to be returned (20 default, 50 at most).                                                              |
| start        | (Optional) First post id to be returned with sort set to id_asc. Ignored if `cursorString` is specified.                                 |
| end          | (Optional) First post id to be returned with sort set to id_desc. Ignored if `cursorString` is specified.                                |
| cursorString | (Optional) for pagination.                                                                                                               |

```kotlin
suspend fun getForumListing(): List<Forum>
```
#### Get Forum Listing

Get top-level forums and their subforums (max 2 deep).

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-forum-listing](https://osu.ppy.sh/docs/index.html#get-forum-listing)

No parameters.

```kotlin
suspend fun getForumAndTopics(forumId): ForumAndTopicsResponse
```
#### Get Forum and Topics

Get a forum by id, its pinned topics, recent topics, and its subforums.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-forum-and-topics](https://osu.ppy.sh/docs/index.html#get-forum-and-topics)

| Attribute | Description                |
|-----------|----------------------------|
| forumId   | Id of the forum.           |

```kotlin
suspend fun editTopic(topicId, title): ForumTopic
```
#### Edit Topic

Edit topic. Only title can be edited through this endpoint.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#edit-topic](https://osu.ppy.sh/docs/index.html#edit-topic)

| Attribute | Description                |
|-----------|----------------------------|
| topicId   | Id of the topic.           |
| title     | New topic title.           |

```kotlin
suspend fun editPost(postId, body): ForumPost
```
#### Edit Post

Edit specified forum post.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#edit-post](https://osu.ppy.sh/docs/index.html#edit-post)

| Attribute | Description                         |
|-----------|-------------------------------------|
| postId    | Id of the post.                     |
| body      | New post content in BBCode format.  |

### Search

```kotlin
suspend fun search(mode, query, page): Search
```
#### Search

Searches users and wiki pages.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#search](https://osu.ppy.sh/docs/index.html#search)

| Attribute | Description                                                |
|-----------|------------------------------------------------------------|  
| mode      | (Optional) Either all, user, or wiki_page. Default is all. |
| query     | (Optional) Search keyword.                                 |
| page      | Search result page. Ignored for mode all.                  |

### Matches

```kotlin
suspend fun getMatchesListing(limit, sort, cursorString): MatchesResponse
```
#### Get Matches Listing

Returns a list of matches.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-matches-listing](https://osu.ppy.sh/docs/index.html#get-matches-listing)

| Attribute    | Description                                                                                |
|--------------|--------------------------------------------------------------------------------------------|
| limit        | (Optional) Maximum number of matches (50 default, 1 minimum, 50 maximum).                 |
| sort         | (Optional) id_desc for newest first; id_asc for oldest first. Defaults to id_desc.        |
| cursorString | for pagination.                                                                            |

```kotlin
suspend fun getMatch(matchId, before, after, limit): MatchResponse
```
#### Get Match

Returns details of the specified match.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-match](https://osu.ppy.sh/docs/index.html#get-match)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| matchId   | Match ID.                                                                                  |
| before    | (Optional) Filter for match events before the specified MatchEvent.id.                    |
| after     | (Optional) Filter for match events after the specified MatchEvent.id.                     |
| limit     | (Optional) Maximum number of match events (100 default, 1 minimum, 101 maximum).          |

### Multiplayer

```kotlin
suspend fun getMultiplayerRooms(limit, mode, seasonId, sort, typeGroup): List<Room>
```
#### Get Multiplayer Rooms

Returns a list of multiplayer rooms.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-match](https://osu.ppy.sh/docs/index.html#get-match)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| limit     | (Optional) Maximum number of results.                                                      |
| mode      | (Optional) Filter mode; active (default), all, ended, participated, owned.                |
| seasonId  | (Optional) Season ID to return Rooms from.                                                 |
| sort      | (Optional) Sort order; ended, created.                                                     |
| typeGroup | (Optional) playlists (default) or realtime.                                                |

```kotlin
suspend fun getMultiplayerScores(roomId, playlistId, limit, sort, cursorString): MultiplayerScores
```
#### Get Multiplayer Scores

Returns a list of scores for specified playlist item.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-scores](https://osu.ppy.sh/docs/index.html#get-scores)

| Attribute    | Description                                                                                |
|--------------|--------------------------------------------------------------------------------------------|
| roomId       | Id of the room.                                                                            |
| playlistId   | Id of the playlist item                                                                    |
| limit        | (Optional) Number of scores to be returned.                                                |
| sort         | (Optional) score_asc or score_desc.                                                        |
| cursorString | (Optional) for pagination.                                                                 |

### News

```kotlin
suspend fun getNewsListing(limit, year, cursorString): NewsListingResponse
```
#### Get News Listing

Returns a list of news posts and related metadata.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-news-listing](https://osu.ppy.sh/docs/index.html#get-news-listing)

| Attribute    | Description                                                                                |
|--------------|--------------------------------------------------------------------------------------------|
| limit        | (Optional) Maximum number of posts (12 default, 1 minimum, 21 maximum).                   |
| year         | Year to return posts from.                                                                 |
| cursorString | for pagination.                                                                            |

```kotlin
suspend fun getNewsPost(slug, key): NewsPost
```
#### Get News Post

Returns details of the specified news post.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-news-post](https://osu.ppy.sh/docs/index.html#get-news-post)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| slug      | News post slug or ID.                                                                      |
| key       | Unset to query by slug, or id to query by ID.                                              |

### OAuth Tokens

```kotlin
suspend fun revokeToken()
```
#### Revoke Current Token

Revokes currently authenticated token.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#revoke-current-token](https://osu.ppy.sh/docs/index.html#revoke-current-token)

### Ranking

```kotlin
suspend fun getKudosuRanking(page): List<User>
```
#### Get Kudosu Ranking

Gets the kudosu ranking.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-kudosu-ranking](https://osu.ppy.sh/docs/index.html#get-kudosu-ranking)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| page      | (Optional) Ranking page.                                                                   |

```kotlin
suspend fun getRanking(mode, type, country, cursor, filter, spotlight, variant): Rankings
```
#### Get Ranking

Gets the current ranking for the specified type and game mode.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-ranking](https://osu.ppy.sh/docs/index.html#get-ranking)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| mode      | Ruleset. User default if not specified.                                                    |
| type      | Ranking type.                                                                               |
| country   | (Optional) Filter ranking by country code. Only available for type of performance.         |
| cursor    | (Optional) Cursor for pagination.                                                           |
| filter    | (Optional) Either all (default) or friends.                                                 |
| spotlight | (Optional) The id of the spotlight if type is charts. Ranking for latest spotlight will be returned if not specified. |
| variant   | (Optional) Filter ranking to specified mode variant. For mode of mania, it's either 4k or 7k. Only available for type of performance. |

```kotlin
suspend fun getSpotlights(): List<Spotlight>
```
#### Get Spotlights

Gets the list of spotlights.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-spotlights](https://osu.ppy.sh/docs/index.html#get-spotlights)

### Wiki

```kotlin
suspend fun getWikiPage(locale, path): WikiPage
```
#### Get Wiki Page

The wiki article or image data.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-wiki-page](https://osu.ppy.sh/docs/index.html#get-wiki-page)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| locale    | Two-letter language code of the wiki page.                                                 |
| path      | The path name of the wiki page.                                                            |
