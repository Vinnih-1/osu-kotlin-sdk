package endpoints.changelog

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import models.Build

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