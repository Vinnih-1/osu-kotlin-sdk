package endpoints.requests.changelog

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.changelog.Build

class GetChangelogBuildRequestImpl(val stream: String, val build: String) : EndpointRequest<Build> {

    override fun endpoint(): String = "changelog/${stream}/${build}"

    override suspend fun request(client: HttpClient): Build {
        val response = client.get(this.url) {
            parameter("stream", stream)
            parameter("build", build)
        }
        return json.decodeFromString<Build>(response.bodyAsText())
    }
}