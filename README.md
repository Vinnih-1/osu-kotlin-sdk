# 🎧 OsuKDK

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blueviolet?logo=kotlin)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/osu!%20API-v2-orange)](https://osu.ppy.sh/docs/index.html)

A Kotlin wrapper for the osu! v2 API designed to simplify integration with the official osu! API endpoints.

---

- [Installation](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-installation)
- [Quickstart](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-quickstart)
- [Examples](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop/src/test/kotlin/endpoints)
- - [Beatmap Pack](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapPackEndpointsTest.kt#L34)
- - [Beatmap Packs](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapPackEndpointsTest.kt#L28)
- - [User Beatmap Score](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L29)
- - [User Beatmap Scores](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L35)
- - [Beatmap Scores](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L41)
- - [Beatmaps](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L47)
- - [Beatmap](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L53)
- - [Beatmap Attributes](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapEndpointsTest.kt#L59)
- - [Beatmapset Discussions Posts](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapsetDiscussionEndpointsTest.kt#L28)
- - [Beatmapset Discussions](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapsetDiscussionEndpointsTest.kt#L40)
- - [Search Beatmapset](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapsetEndpointsTest.kt#L28)
- - [Beatmapset](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapsetEndpointsTest.kt#L34)
- - [Beatmapset Events](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/BeatmapsetEndpointsTest.kt#L40)
- - [Changelog Build](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/ChangelogEndpointsTest.kt#L28)
- - [Changelog Listing](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/ChangelogEndpointsTest.kt#L34)
- - [Lookup Changelog Build](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/ChangelogEndpointsTest.kt#L40)
- - [Scores](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/ScoreEndpointsTest.kt#L35)
- - [Download Score](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/ScoreEndpointsTest.kt#L29)
- - [User](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L32)
- - [Users](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L62)
- - [User Kudosu](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L68)
- - [User Score](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L38)
- - [User Beatmaps](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L44)
- - [User Recent Activity](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L50)
- - [Search Beatmaps Passed](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L56)
- - [Own Data](https://github.com/Vinnih-1/osu-kotlin-sdk/blob/develop/src/test/kotlin/endpoints/UserEndpointsTest.kt#L74)

## 📦 Installation

You can add OsuKDK to your project using Gradle or Maven:

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.vinnih-1:osukdk:0.1.0-alpha")
```

### Maven

```xml
<dependency>
    <groupId>io.github.vinnih-1</groupId>
    <artifactId>osukdk</artifactId>
    <version>0.1.0-ALPHA</version>
</dependency>
```
---

## 🚀 Quickstart

```kotlin
val api = Authorization(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314)
println(user.username)
