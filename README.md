# 🎧 OsuKDK ([Documentation](https://vinnih-1.github.io/osu-kotlin-sdk/))

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blueviolet?logo=kotlin)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/osu!%20API-v2-orange)](https://osu.ppy.sh/docs/index.html)

A Kotlin wrapper for the osu! v2 API designed to simplify integration with the official osu! API endpoints.

This project needs to be used with Kotlin Coroutines, you can see more informations [here](https://github.com/Kotlin/kotlinx.coroutines).

---

- [Installation](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-installation)
- [Quickstart](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-quickstart)

## 📦 Installation

You can add OsuKDK to your project using Gradle or Maven:

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.vinnih-1:osukdk:0.3.0-alpha")
```

### Maven

```xml
<dependency>
    <groupId>io.github.vinnih-1</groupId>
    <artifactId>osukdk</artifactId>
    <version>0.3.0-ALPHA</version>
</dependency>
```
---

## 🚀 Quickstart

```kotlin
val api = Authorization(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314)
println(user.username)
