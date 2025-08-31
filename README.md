# 🎧 OsuKDK ([Documentation](https://vinnih-1.github.io/osu-kotlin-sdk/))

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blueviolet?logo=kotlin)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/osu!%20API-v2-orange)](https://osu.ppy.sh/docs/index.html)

A Kotlin wrapper for the osu! v2 API designed to simplify integration with the official osu! API endpoints.

This project needs to be used with Kotlin Coroutines, you can see more information [here](https://github.com/Kotlin/kotlinx.coroutines).

---

- [Installation](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/master?tab=readme-ov-file#installation)
- [Quickstart](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/master?tab=readme-ov-file#quickstart)
- [Using chat](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/master?tab=readme-ov-file#using-chat)
- [Requirements](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/master?tab=readme-ov-file#requirements)

## Installation

You can add OsuKDK to your project using Gradle or Maven:

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.vinnih-1:osukdk:1.0.2-beta")
```

### Maven

```xml
<dependency>
    <groupId>io.github.vinnih-1</groupId>
    <artifactId>osukdk</artifactId>
    <version>1.0.2-beta</version>
</dependency>
```
---

## Quickstart

```kotlin
val api = Auth(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314).also { println(it.username) }
```

## Using chat

```kotlin
val api = Auth(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).apply {
    redirectUri = "http://localhost:3914" // This must match the registered Application Callback URL exactly.
    scopes = listOf(Scopes.PUBLIC, Scopes.CHAT_READ, Scopes.CHAT_WRITE_MANAGE, Scopes.CHAT_WRITE)
}.create()

api.sendPM(21009314, "Hello Vinnih! :D").also { (message) ->
    println(message.content)
}
```

## Requirements

- [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)

## I found a bug / I have a suggestion

You're always welcome to open an [issue](https://github.com/Vinnih-1/osu-kotlin-sdk/issues). I'd really appreciate it.