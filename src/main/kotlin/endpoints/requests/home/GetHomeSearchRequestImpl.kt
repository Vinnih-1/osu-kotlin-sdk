package endpoints.requests.home

import OsuKDK.Companion.json
import endpoints.requests.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.home.Search

class GetHomeSearchRequestImpl(
    val mode: String?,
    val query: String?,
    val page: Int?
) : EndpointRequest<Search> {

    override fun endpoint(): String = "search"

    override suspend fun request(client: HttpClient): Search {
        val response = client.get(this.url) {
            parameter("mode", mode)
            parameter("query", query)
            parameter("page", page)
        }
        return json.decodeFromString<Search>(response.bodyAsText())
    }
}