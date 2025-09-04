## Installation

### Gradle (Kotlin DSL)

This project needs to be used with Kotlin Coroutines, you can see more information [here](https://github.com/Kotlin/kotlinx.coroutines).

```kotlin
implementation("io.github.vinnih-1:osukdk:1.1.0-beta")
```

### Maven

```xml
<dependency>
    <groupId>io.github.vinnih-1</groupId>
    <artifactId>osukdk</artifactId>
    <version>1.1.0-beta</version>
</dependency>
```

## Creating a Client

### Client Credentials Grant

```kotlin
val api = Auth(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).create()
val user = api.getUser(21009314).also { println(it.username) }
```

## Using chat

> If you specify the `REDIRECT_URI` you will get an `Authorization Code Grant` automatically.

```kotlin
val api = Auth(YOUR_CLIENT_ID, YOUR_CLIENT_SECRET).apply {
    redirectUri = "http://localhost:3914" // This must match the registered Application Callback URL exactly.
    scopes = listOf(Scopes.PUBLIC, Scopes.CHAT_READ, Scopes.CHAT_WRITE_MANAGE, Scopes.CHAT_WRITE)
}.create()

api.sendPM(21009314, "Hello Vinnih! :D").also { (message) ->
    println(message.content)
}
```

---

> ## **Next step: [Endpoints](endpoints/)**