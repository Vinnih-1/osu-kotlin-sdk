package endpoints.requests

import io.ktor.client.*

interface EndpointRequest<T> {

    val domain: String
        get() = "osu.ppy.sh"

    val apiVersion: String
        get() = "v2"

    val url: String
        get() = "https://$domain/api/$apiVersion/${endpoint()}"

    fun endpoint(): String

    suspend fun request(client: HttpClient): T
}