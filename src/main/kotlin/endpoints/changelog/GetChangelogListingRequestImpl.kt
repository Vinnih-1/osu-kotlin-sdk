package endpoints.changelog

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import models.Build

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

@Serializable
data class BuildResponse(
    val builds: List<Build>,
    val streams: List<Build.UpdateStream>,
    val search: Search? = null
) {

    @Serializable
    data class Search(
        val from: String? = null,
        val limit: Int? = null,
        val maxId: Int? = null,
        val stream: String? = null,
        val to: String? = null,

    )
}