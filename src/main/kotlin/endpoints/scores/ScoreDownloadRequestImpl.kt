package endpoints.scores

import endpoints.EndpointRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*

class ScoreDownloadRequestImpl(val scoreId: Long, val isOldFormat: Boolean?) : EndpointRequest<ByteArray> {

    override fun endpoint(): String = "scores/${if (isOldFormat == true) "osu/" else ""}${scoreId}/download"

    override suspend fun request(client: HttpClient): ByteArray {
        val response = client.get(this.url)
        return response.bodyAsChannel().toByteArray()
    }
}