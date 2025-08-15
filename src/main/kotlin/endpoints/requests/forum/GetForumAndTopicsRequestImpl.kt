package endpoints.requests.forum

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.forums.ForumAndTopicsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetForumAndTopicsRequestImpl(
    val forumId: Int
) : EndpointRequest<ForumAndTopicsResponse> {
    override fun endpoint(): String = "forums/${forumId}"

    override suspend fun request(client: HttpClient): ForumAndTopicsResponse {
        val response = client.get(this.url)
        return json.decodeFromString<ForumAndTopicsResponse>(response.bodyAsText())
    }
}