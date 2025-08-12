package models.home

import kotlinx.serialization.Serializable
import models.wiki.WikiPage
import models.users.User

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