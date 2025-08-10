package models

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val user: SearchResult<User>? = null,
    val wikiPage: SearchResult<WikiPage>? = null
) {

    @Serializable
    data class SearchResult<T>(
        val data: List<T>,
        val total: Int
    )
}