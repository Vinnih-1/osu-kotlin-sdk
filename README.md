# 🎧 OsuKDK

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blueviolet?logo=kotlin)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/osu!%20API-v2-orange)](https://osu.ppy.sh/docs/index.html)

A Kotlin wrapper for the osu! v2 API designed to simplify integration with the official osu! API endpoints.
---

- [Installation](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-installation)
- [Quickstart](https://github.com/Vinnih-1/osu-kotlin-sdk/tree/develop#-quickstart)

## 📦 Installation

> 🚧 Future releases will include an easy import Maven Central.
---

## 🚀 Quickstart

```kotlin
val api = Authorization(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314)
println(user.username)
