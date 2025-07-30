package endpoints

import credentials.Authorization
import io.ktor.client.HttpClient

interface EndpointRequest<T> {

    val domain: String
        get() = Authorization.DOMAIN

    val apiVersion: String
        get() = "v2"

    val url: String
        get() = "https://$domain/api/$apiVersion/${endpoint()}"

    fun endpoint(): String = ""

    suspend fun request(client: HttpClient): T
}