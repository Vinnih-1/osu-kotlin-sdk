package endpoints.comments

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import enums.CommentSort
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.comments.CommentBundle

class GetCommentsRequestImpl(
    val after: Int?,
    val commentableType: String?,
    val commentableId: Int?,
    val cursor: String?,
    val parentId: Int?,
    val sort: CommentSort?
) : EndpointRequest<CommentBundle> {

    override fun endpoint(): String = "comments"

    override suspend fun request(client: HttpClient): CommentBundle {
        val response = client.get(this.url) {
            parameter("after", after)
            parameter("commentable_type", commentableType)
            parameter("commentable_id", commentableId)
            parameter("cursor", cursor)
            parameter("parent_id", parentId)
            parameter("sort", sort?.value)
        }
        return json.decodeFromString<CommentBundle>(response.bodyAsText())
    }
}