# OsuKDK
---

## Installation

### Gradle (Kotlin DSL)

This project needs to be used with Kotlin Coroutines, you can see more information [here](https://github.com/Kotlin/kotlinx.coroutines).

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

#### Get Beatmap Packs

```kotlin
suspend fun getBeatmapPacks(type: BeatmapPackType? = BeatmapPackType.STANDARD, cursor: String? = ""): BeatmapPackResponse
```

Returns a list of beatmap packs.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-packs](https://osu.ppy.sh/docs/index.html#get-beatmap-packs)

| Attribute | Description                                                      |
|-----------|------------------------------------------------------------------|
| type      | (Optional) of the beatmap packs to be returned. Defaults to standard. |
| cursor    | (Optional) for pagination.                                       |

#### Get Beatmap Pack

```kotlin
suspend fun getBeatmapPack(pack: String, legacyOnly: Boolean? = false): BeatmapPack
```

Gets the beatmap pack for the specified beatmap pack tag.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-pack](https://osu.ppy.sh/docs/index.html#get-beatmap-pack)

| Attribute  | Description                                                                                     |
|------------|-------------------------------------------------------------------------------------------------|
| pack       | The tag of the beatmap pack to be returned.                                                     |
| legacyOnly | (Optional) Whether or not to consider lazer scores for user completion data. Defaults to false. |

### Beatmaps

#### Get a User Beatmap score

```kotlin
suspend fun getUserBeatmapScore(beatmapId: Int, userId: Int, legacyOnly: Boolean? = false, mode: ModeEnum? = ModeEnum.OSU, mods: String? = ""): BeatmapUserScore
```

Return a User's score on a Beatmap

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-score](https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-score)

| Attribute  | Description                                                                |
|------------|----------------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                         |
| userId     | Id of the User.                                                            |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false.      |
| mode       | (Optional) The Ruleset to get scores for.                                  |
| mods       | (Optional) An array of matching Mods, or none.                             |

#### Get a User Beatmap scores

```kotlin
suspend fun getUserBeatmapScores(beatmapId: Int, userId: Int, legacyOnly: Boolean? = false, mode: ModeEnum? = ModeEnum.OSU): UserBeatmapsScoresResponse
```

Return a User's scores on a Beatmap

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-scores](https://osu.ppy.sh/docs/index.html#get-a-user-beatmap-scores)

| Attribute  | Description                                                           |
|------------|-----------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                    |
| userId     | Id of the User.                                                       |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false. |
| mode       | (Optional) The Ruleset to get scores for.                             |

#### Get Beatmap scores

```kotlin
suspend fun getBeatmapScores(beatmapId: Int, legacyOnly: Boolean? = false, mode: ModeEnum? = ModeEnum.OSU, mods: String? = "", type: String? = ""): BeatmapScores
```

Returns the top scores for a beatmap. Depending on user preferences, this may only show legacy scores.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-scores](https://osu.ppy.sh/docs/index.html#get-beatmap-scores)

| Attribute  | Description                                                           |
|------------|-----------------------------------------------------------------------|
| beatmapId  | Id of the Beatmap.                                                    |
| legacyOnly | (Optional) Whether or not to exclude lazer scores. Defaults to false. |
| mode       | (Optional) The Ruleset to get scores for.                             |
| mods       | (Optional) An array of matching Mods, or none.                        |
| type       | (Optional) Beatmap score ranking type.                                |

#### Get Beatmaps

```kotlin
suspend fun getBeatmaps(ids: List<Int>? = listOf()): BeatmapsResponse
```

Returns a list of beatmaps.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmaps](https://osu.ppy.sh/docs/index.html#get-beatmaps)

| Attribute | Description                                                                                                |
|-----------|------------------------------------------------------------------------------------------------------------|  
| ids       | Beatmap IDs to be returned. Specify once for each beatmap ID requested. Up to 50 beatmaps can be requested at once. |

#### Get Beatmap

```kotlin
suspend fun getBeatmap(beatmapId: Int): Beatmap
```

Gets beatmap data for the specified beatmap ID.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap](https://osu.ppy.sh/docs/index.html#get-beatmap)

| Attribute | Description                |
|-----------|----------------------------|
| beatmapId | The ID of the beatmap.     |

#### Get Beatmap Attributes

```kotlin
suspend fun getBeatmapAttributes(beatmapId: Int, mods: List<ModLegacy>, mode: ModeEnum): BeatmapDifficultyAttributesResponse
```

Returns difficulty attributes of beatmap with specific mode and mods combination.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-beatmap-attributes](https://osu.ppy.sh/docs/index.html#get-beatmap-attributes)

| Attribute | Description                                                                                                                                                                                                |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| beatmapId | The ID of the beatmap.                                                                                                                                                                                     |
| mods      | array of mods                                                                                                                                                                                              |
| mode      | Ruleset of the difficulty attributes. Only valid if it's the beatmap ruleset or the beatmap can be converted to the specified ruleset. Defaults to ruleset of the specified beatmap.                        |

### Beatmapset Discussions

#### Get Beatmapset Discussion Posts

```kotlin
suspend fun getBeatmapsetDiscussionPosts(beatmapsetDiscussionId: String? = null, limit: Int? = 100, page: Int? = null, sort: Sort? = Sort.NEWEST, types: List<BeatmapsetDiscussionPostTypes>? = listOf(BeatmapsetDiscussionPostTypes.REPLY), userId: String? = null, withDeleted: String? = null): BeatmapsetDiscussionPostsResponse
```

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

#### Get Beatmapset Discussion Votes

```kotlin
suspend fun getBeatmapsetDiscussionVotes(beatmapsetDiscussionId: String? = null, limit: Int? = 100, page: Int? = null, receiver: String? = null, score: String? = null, sort: Sort? = Sort.NEWEST, userId: String? = null, withDeleted: String? = null): BeatmapsetDiscussionVotesResponse
```

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

#### Get Beatmapset Discussions

```kotlin
suspend fun getBeatmapsetDiscussion(beatmapId: String? = null, beatmapsetId: String? = null, beatmapsetStatus: String? = null, limit: Int? = 100, messageTypes: List<String>? = null, onlyUnresolved: Boolean? = false, page: Int? = null, sort: Sort? = Sort.NEWEST, userId: String? = null, withDeleted: String? = null, cursorString: String? = null): BeatmapsetDiscussionResponse
```

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

#### Search Beatmapset

```kotlin
suspend fun searchBeatmapset(cursorString: String? = null): SearchBeatmapsetResponse
```

TODO: DOCS

| Attribute    | Description                |
|--------------|----------------------------|
| cursorString | CursorString for pagination. |

#### Get Beatmapset

```kotlin
suspend fun getBeatmapset(beatmapsetId: Int): Beatmapset
```

TODO: DOCS

| Attribute    | Description                |
|--------------|----------------------------|
| beatmapsetId | The ID of the beatmapset.  |

#### Get Beatmapset Events

```kotlin
suspend fun getBeatmapsetEvents(): BeatmapsetEventsResponse
```

TODO: DOCS

No parameters.

### Changelog

#### Get Changelog Build

```kotlin
suspend fun getChangelogBuild(stream: String, build: String): Build
```

Returns details of the specified build.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-changelog-build](https://osu.ppy.sh/docs/index.html#get-changelog-build)

| Attribute | Description                |
|-----------|----------------------------|
| stream    | (Optional) Update stream name. |
| build     | (Optional) Build version.  |

#### Get Changelog Listing

```kotlin
suspend fun getChangelogListing(from: String? = null, maxId: Int? = null, stream: String? = null, to: String? = null, messageFormats: List<String>? = listOf("html, markdown")): BuildResponse
```

Returns a listing of update streams, builds, and changelog entries.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-changelog-listing](https://osu.ppy.sh/docs/index.html#get-changelog-listing)

| Attribute      | Description                                                |
|----------------|------------------------------------------------------------|  
| from           | (Optional) Minimum build version.                           |
| maxId          | (Optional) Maximum build ID.                                |
| stream         | (Optional) Stream name to return builds from.               |
| to             | (Optional) Maximum build version.                           |
| messageFormats | (Optional) html, markdown. Default to both.                |

#### Lookup Changelog Build

```kotlin
suspend fun lookupChangelogBuild(build: String, messageFormats: List<String>? = listOf("html, markdown")): Build
```

Returns details of the specified build.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#lookup-changelog-build](https://osu.ppy.sh/docs/index.html#lookup-changelog-build)

| Attribute      | Description                                                |
|----------------|------------------------------------------------------------|  
| build          | (Optional) Build version, update stream name, or build ID. |
| messageFormats | (Optional) html, markdown. Default to both.                |

### Scores

#### Download Score

```kotlin
suspend fun downloadScore(scoreId: Long, isOldFormat: Boolean? = false): ByteArray
```

This method returns ByteArray from Score

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-apiv2scoresscoredownload](https://osu.ppy.sh/docs/index.html#get-apiv2scoresscoredownload)

| Attribute   | Description                                                                                                                                                                                                             |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| scoreId     | Id of the Score                                                                                                                                                                                                         |
| isOldFormat | If your score ID is in the old format (https://osu.ppy.sh/scores/osu/4459998279) this param should be true, or if is in new format (https://osu.ppy.sh/scores/1695006824) you can ignore this param. Defaults to false. |

#### Get Scores

```kotlin
suspend fun getScore(mode: ModeEnum? = ModeEnum.OSU, cursorString: String? = null): ScoreResponse
```

Returns all passed scores. Up to 1000 scores will be returned in order of oldest to latest. Most recent scores will be returned if cursor_string parameter is not specified.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-scores97](https://osu.ppy.sh/docs/index.html#get-scores97)

| Attribute    | Description                                |
|--------------|--------------------------------------------|
| mode         | (Optional) The Ruleset to get scores for.  |
| cursorString | (Optional) Next set of scores              |

### Users

#### Get User

```kotlin
suspend fun getUser(userId: Int, mode: ModeEnum = ModeEnum.OSU): User
```

This endpoint returns the detail of specified user.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user](https://osu.ppy.sh/docs/index.html#get-user)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| mode      | (Optional) osu mode will be used if not specified.          |

#### Get Users

```kotlin
suspend fun getUsers(ids: List<String>, includeVariantStatistics: Boolean? = true): UsersResponse
```

Returns list of users.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-users](https://osu.ppy.sh/docs/index.html#get-users)

| Attribute                | Description                                                                                                |
|--------------------------|------------------------------------------------------------------------------------------------------------|
| ids                      | User id to be returned. Specify once for each user id requested. Up to 50 users can be requested at once.   |
| includeVariantStatistics | (Optional) Whether to additionally include statistics variants (default: true).                            |

#### Get User Kudosu

```kotlin
suspend fun getUserKudosu(userId: Int, limit: Int? = 50, offset: String? = "0"): List<KudosuHistory>
```

Returns kudosu history

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-kudosu](https://osu.ppy.sh/docs/index.html#get-user-kudosu)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| limit     | (Optional) Maximum number of results. defaults to 50        |
| offset    | (Optional) Result offset for pagination. defaults to 0      |

#### Get User Scores

```kotlin
suspend fun getUserScore(userId: Int, type: ScoreType? = ScoreType.RECENT, legacyOnly: Boolean? = false, includeFails: Boolean? = false, mode: ModeEnum? = ModeEnum.OSU, offset: Int? = 0, limit: Int? = 100): List<Score>
```

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

#### Get User Beatmaps

```kotlin
suspend fun getUserBeatmaps(userId: Int, type: BeatmapPlaycountType, offset: Int? = 0, limit: Int? = 100): List<BeatmapPlayCount>
```

Returns the beatmaps of specified user.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-beatmaps](https://osu.ppy.sh/docs/index.html#get-user-beatmaps)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| type      | Beatmap type.                                               |
| offset    | (Optional) Result offset for pagination. default is 0       |
| limit     | (Optional) Maximum number of results. default is 100        |

#### Get User Recent Activity

```kotlin
suspend fun getUserRecentActivity(userId: Int, offset: Int? = 0, limit: Int? = 100): List<Event>
```

Returns recent activity.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-user-recent-activity](https://osu.ppy.sh/docs/index.html#get-user-recent-activity)

| Attribute | Description                                                |
|----------------|------------------------------------------------------------|  
| userId    | Id of the user.                                             |
| offset    | (Optional) Result offset for pagination. default is 0       |
| limit     | (Optional) Maximum number of results. default is 100        |

#### Search Beatmaps Passed

```kotlin
suspend fun searchBeatmapsPassed(userId: Int, beatmapsetIds: List<Int>? = listOf(), excludeConverts: Boolean? = false, isLegacy: Boolean? = true, noDiffReduction: Boolean? = true, rulesetId: Int? = null): SearchBeatmapsPassedResponse
```

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

#### Get Own Data

```kotlin
suspend fun getOwnData(mode: ModeEnum? = ModeEnum.OSU): User
```

Similar to Get User but with authenticated user (token owner) as user id.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-own-data](https://osu.ppy.sh/docs/index.html#get-own-data)

| Attribute | Description                                                |
|-----------|------------------------------------------------------------|
| mode      | (Optional) osu mode will be used if not specified.         |

### Comments

#### Get Comments

```kotlin
suspend fun getComments(after: Int? = null, commentableType: String? = null, commentableId: Int? = null, cursor: String? = null, parentId: Int? = null, sort: CommentSort? = CommentSort.NEW): CommentBundle
```

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

#### Get Comment

```kotlin
suspend fun getComment(commentId: Int): CommentBundle
```

Gets a comment and its replies up to 2 levels deep.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-comment](https://osu.ppy.sh/docs/index.html#get-comment)

| Attribute | Description                |
|-----------|----------------------------|
| commentId | The comment ID.            |

### Events

#### Get Events

```kotlin
suspend fun getEvents(sort: String? = "id_desc", cursorString: String? = null): EventsResponse
```

Returns a collection of Events in order of creation time.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-events](https://osu.ppy.sh/docs/index.html#get-events)

| Attribute    | Description                                                                |
|--------------|----------------------------------------------------------------------------|
| sort         | (Optional) Sorting option. Valid values are id_desc (default) and id_asc. |
| cursorString | (Optional) CursorString for pagination.                                   |

### Forums

#### Reply Topic

```kotlin
suspend fun replyTopic(topicId: Int, body: String): ForumPost
```

Create a post replying to the specified topic.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#reply-topic](https://osu.ppy.sh/docs/index.html#reply-topic)

| Attribute | Description                       |
|-----------|-----------------------------------|
| topicId   | Id of the topic to be replied to. |
| body      | Content of the reply post.        |

#### Get Topic Listing

```kotlin
suspend fun getTopicListing(forumId: String? = null, sort: String? = "new", limit: Int? = 50, cursorString: String? = null): ForumTopicResponse
```

Get a sorted list of topics, optionally from a specific forum

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-topic-listing](https://osu.ppy.sh/docs/index.html#get-topic-listing)

| Attribute    | Description                                                                                            |
|--------------|--------------------------------------------------------------------------------------------------------|
| forumId      | Id of a specific forum to get topics from.                                                             |
| sort         | Topic sorting option. Valid values are new (default) and old. Both sort by the topic's last post time. |
| limit        | Maximum number of topics to be returned (50 at most and by default).                                   |
| cursorString | for pagination.                                                                                        |

#### Create Topic

```kotlin
suspend fun createTopic(body: String, forumId: Int, title: String, withPoll: Boolean? = false, pollHideResults: Boolean? = false, pollLengthDays: Int? = 0, pollMaxOptions: Int? = 1, pollOptions: String? = null, pollTitle: String? = null, pollVoteChange: Boolean? = false): CreateTopicResponse
```

Create a new topic.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#create-topic](https://osu.ppy.sh/docs/index.html#create-topic)

| Attribute        | Description                                                                                                |
|------------------|------------------------------------------------------------------------------------------------------------|
| body             | Content of the topic.                                                                                      |
| forumId          | Forum to create the topic in.                                                                              |
| title            | Title of the topic.                                                                                        |
| withPoll         | Enable this to also create poll in the topic (default: false).                                             |
| pollHideResults  | Enable this to hide result until voting period ends (default: false).                                     |
| pollLengthDays   | Number of days for voting period. 0 means the voting will never ends (default: 0). This parameter is required if hide_results option is enabled. |
| pollMaxOptions   | Maximum number of votes each user can cast (default: 1).                                                   |
| pollOptions      | Newline-separated list of voting options. BBCode is supported.                                             |
| pollTitle        | Title of the poll.                                                                                         |
| pollVoteChange   | Enable this to allow user to change their votes (default: false).                                         |

#### Get Topic and Posts

```kotlin
suspend fun getTopicAndPosts(topicId: Int, sort: String? = "id_asc", limit: Int? = 20, start: String? = null, end: String? = null, cursorString: String? = null): ForumTopicAndPostsResponse
```

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

#### Get Forum Listing

```kotlin
suspend fun getForumListing(): List<Forum>
```

Get top-level forums and their subforums (max 2 deep).

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-forum-listing](https://osu.ppy.sh/docs/index.html#get-forum-listing)

No parameters.

#### Get Forum and Topics

```kotlin
suspend fun getForumAndTopics(forumId: Int): ForumAndTopicsResponse
```

Get a forum by id, its pinned topics, recent topics, and its subforums.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-forum-and-topics](https://osu.ppy.sh/docs/index.html#get-forum-and-topics)

| Attribute | Description                |
|-----------|----------------------------|
| forumId   | Id of the forum.           |

#### Edit Topic

```kotlin
suspend fun editTopic(topicId: Int, title: String): ForumTopic
```

Edit topic. Only title can be edited through this endpoint.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#edit-topic](https://osu.ppy.sh/docs/index.html#edit-topic)

| Attribute | Description                |
|-----------|----------------------------|
| topicId   | Id of the topic.           |
| title     | New topic title.           |

#### Edit Post

```kotlin
suspend fun editPost(postId: Int, body: String): ForumPost
```

Edit specified forum post.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#edit-post](https://osu.ppy.sh/docs/index.html#edit-post)

| Attribute | Description                         |
|-----------|-------------------------------------|
| postId    | Id of the post.                     |
| body      | New post content in BBCode format.  |

### Search

#### Search

```kotlin
suspend fun search(mode: String? = "all", query: String? = null, page: Int? = null): Search
```

Searches users and wiki pages.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#search](https://osu.ppy.sh/docs/index.html#search)

| Attribute | Description                                                |
|-----------|------------------------------------------------------------|  
| mode      | (Optional) Either all, user, or wiki_page. Default is all. |
| query     | (Optional) Search keyword.                                 |
| page      | Search result page. Ignored for mode all.                  |

### Matches

#### Get Matches Listing

```kotlin
suspend fun getMatchesListing(limit: Int? = 50, sort: String? = "id_desc", cursorString: String? = null): MatchesResponse
```

Returns a list of matches.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-matches-listing](https://osu.ppy.sh/docs/index.html#get-matches-listing)

| Attribute    | Description                                                                                |
|--------------|--------------------------------------------------------------------------------------------|
| limit        | (Optional) Maximum number of matches (50 default, 1 minimum, 50 maximum).                 |
| sort         | (Optional) id_desc for newest first; id_asc for oldest first. Defaults to id_desc.        |
| cursorString | for pagination.                                                                            |

#### Get Match

```kotlin
suspend fun getMatch(matchId: Long, before: Int? = null, after: Int? = null, limit: Int? = 100): MatchResponse
```

Returns details of the specified match.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-match](https://osu.ppy.sh/docs/index.html#get-match)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| matchId   | Match ID.                                                                                  |
| before    | (Optional) Filter for match events before the specified MatchEvent.id.                    |
| after     | (Optional) Filter for match events after the specified MatchEvent.id.                     |
| limit     | (Optional) Maximum number of match events (100 default, 1 minimum, 101 maximum).          |

### Multiplayer

#### Get Multiplayer Rooms

```kotlin
suspend fun getMultiplayerRooms(limit: Int? = null, mode: String? = "active", seasonId: String? = null, sort: String? = null, typeGroup: String? = "playlists"): List<Room>
```

Returns a list of multiplayer rooms.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-match](https://osu.ppy.sh/docs/index.html#get-match)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| limit     | (Optional) Maximum number of results.                                                      |
| mode      | (Optional) Filter mode; active (default), all, ended, participated, owned.                |
| seasonId  | (Optional) Season ID to return Rooms from.                                                 |
| sort      | (Optional) Sort order; ended, created.                                                     |
| typeGroup | (Optional) playlists (default) or realtime.                                                |

#### Get Multiplayer Scores

```kotlin
suspend fun getMultiplayerScores(roomId: Int, playlistId: Int, limit: Int? = null, sort: String? = null, cursorString: String? = null): MultiplayerScores
```

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

#### Get News Listing

```kotlin
suspend fun getNewsListing(limit: Int? = 12, year: Int? = null, cursorString: String? = null): NewsListingResponse
```

Returns a list of news posts and related metadata.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-news-listing](https://osu.ppy.sh/docs/index.html#get-news-listing)

| Attribute    | Description                                                                                |
|--------------|--------------------------------------------------------------------------------------------|
| limit        | (Optional) Maximum number of posts (12 default, 1 minimum, 21 maximum).                   |
| year         | Year to return posts from.                                                                 |
| cursorString | for pagination.                                                                            |

#### Get News Post

```kotlin
suspend fun getNewsPost(slug: String, key: String? = null): NewsPost
```

Returns details of the specified news post.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-news-post](https://osu.ppy.sh/docs/index.html#get-news-post)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| slug      | News post slug or ID.                                                                      |
| key       | Unset to query by slug, or id to query by ID.                                              |

### OAuth Tokens

#### Revoke Current Token

```kotlin
suspend fun revokeToken()
```

Revokes currently authenticated token.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#revoke-current-token](https://osu.ppy.sh/docs/index.html#revoke-current-token)

### Ranking

#### Get Kudosu Ranking

```kotlin
suspend fun getKudosuRanking(page: Int? = null): KudosuRankingResponse
```

Gets the kudosu ranking.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-kudosu-ranking](https://osu.ppy.sh/docs/index.html#get-kudosu-ranking)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| page      | (Optional) Ranking page.                                                                   |

#### Get Ranking

```kotlin
suspend fun getRanking(rankingType: RankingType, mode: ModeEnum, country: String? = null, cursor: Rankings.Cursor? = null, filter: String? = "all", spotlight: String? = null, variant: String? = null): Rankings
```

Gets the current ranking for the specified type and game mode.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-ranking](https://osu.ppy.sh/docs/index.html#get-ranking)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| rankingType | Ranking type.                                                                               |
| mode      | Ruleset. User default if not specified.                                                    |
| country   | (Optional) Filter ranking by country code. Only available for type of performance.         |
| cursor    | (Optional) Cursor for pagination.                                                           |
| filter    | (Optional) Either all (default) or friends.                                                 |
| spotlight | (Optional) The id of the spotlight if type is charts. Ranking for latest spotlight will be returned if not specified. |
| variant   | (Optional) Filter ranking to specified mode variant. For mode of mania, it's either 4k or 7k. Only available for type of performance. |

#### Get Spotlights

```kotlin
suspend fun getSpotlights(): SpotlightsRankingResponse
```

Gets the list of spotlights.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-spotlights](https://osu.ppy.sh/docs/index.html#get-spotlights)

### Wiki

#### Get Wiki Page

```kotlin
suspend fun getWikiPage(locale: String, path: String): WikiPage
```

The wiki article or image data.

Implements endpoint: [https://osu.ppy.sh/docs/index.html#get-wiki-page](https://osu.ppy.sh/docs/index.html#get-wiki-page)

| Attribute | Description                                                                                |
|-----------|--------------------------------------------------------------------------------------------|
| locale    | Two-letter language code of the wiki page.                                                 |
| path      | The path name of the wiki page.                                                            |
