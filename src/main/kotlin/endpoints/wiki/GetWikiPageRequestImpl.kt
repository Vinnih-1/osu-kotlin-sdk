package endpoints.wiki

import OsuKDK.Companion.json
import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.WikiPage

class GetWikiPageRequestImpl(
    val locale: String,
    val path: String
) : EndpointRequest<WikiPage> {

    override fun endpoint(): String = "wiki/${locale}/${path}"

    override suspend fun request(client: HttpClient): WikiPage {
        val response = client.get(this.url)
        return json.decodeFromString<WikiPage>(response.bodyAsText())
    }
}