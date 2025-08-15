package endpoints.responses.changelog

import kotlinx.serialization.Serializable
import models.changelog.Build

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