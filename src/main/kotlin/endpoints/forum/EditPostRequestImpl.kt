package endpoints.forum

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.forums.ForumPost

class EditPostRequestImpl(val postId: Int, val bodyString: String) : EndpointRequest<ForumPost> {

    override fun endpoint(): String = "forums/posts/${postId}"

    override suspend fun request(client: HttpClient): ForumPost {
        val response = client.put(this.url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(mapOf(
                "body" to bodyString
            )))
        }
        return json.decodeFromString<ForumPost>(response.bodyAsText())
    }
}