package endpoints.comments

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import models.CommentBundle

class GetCommentRequestImpl(val commentId: Int) : EndpointRequest<CommentBundle> {

    override fun endpoint(): String = "comments/${commentId}"

    override suspend fun request(client: HttpClient): CommentBundle {
        val response = client.get(this.url)
        return json.decodeFromString<CommentBundle>(response.bodyAsText())
    }
}