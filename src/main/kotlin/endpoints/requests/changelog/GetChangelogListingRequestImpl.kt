package endpoints.requests.changelog

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import endpoints.responses.changelog.BuildResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GetChangelogListingRequestImpl(
    val from: String?,
    val maxId: Int?,
    val stream: String?,
    val to: String?,
    val messageFormats: List<String>?
) : EndpointRequest<BuildResponse> {

    override fun endpoint(): String = "changelog"

    override suspend fun request(client: HttpClient): BuildResponse {
        val response = client.get(this.url) {
            parameter("from", from)
            parameter("max_id", maxId)
            parameter("stream", stream)
            parameter("to", to)
            messageFormats?.forEach { parameter("message_formats[]", it) }
        }
        return json.decodeFromString<BuildResponse>(response.bodyAsText())
    }
}