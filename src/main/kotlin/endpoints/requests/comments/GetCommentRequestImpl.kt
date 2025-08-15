package endpoints.requests.comments

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.comments.CommentBundle

class GetCommentRequestImpl(val commentId: Int) : EndpointRequest<CommentBundle> {

    override fun endpoint(): String = "comments/${commentId}"

    override suspend fun request(client: HttpClient): CommentBundle {
        val response = client.get(this.url)
        return json.decodeFromString<CommentBundle>(response.bodyAsText())
    }
}