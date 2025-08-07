package endpoints.changelog

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.Build

class LookupChangelogBuildRequestImpl(val buildId: String, val messageFormats: List<String>?) : EndpointRequest<Build> {

    override fun endpoint(): String = "changelog/${buildId}"

    override suspend fun request(client: HttpClient): Build {
        val response = client.get(this.url) {
            messageFormats?.forEach { parameter("message_formats[]", it) }
        }
        return json.decodeFromString<Build>(response.bodyAsText())
    }
}