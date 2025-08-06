package endpoints.scores

import endpoints.EndpointRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.toByteArray

class ScoreDownloadRequestImpl(val scoreId: Long, val isOldFormat: Boolean?) : EndpointRequest<ByteArray> {

    override fun endpoint(): String = "scores/${if (isOldFormat == true) "osu/" else ""}${scoreId}/download"

    override suspend fun request(client: HttpClient): ByteArray {
        val response = client.get(this.url)
        return response.bodyAsChannel().toByteArray()
    }
}