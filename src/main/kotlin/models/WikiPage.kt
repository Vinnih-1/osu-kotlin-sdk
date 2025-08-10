package models

import kotlinx.serialization.Serializable

@Serializable
data class WikiPage(
    val availableLocales: List<String>,
    val layout: String,
    val locale: String,
    val markdown: String,
    val path: String,
    val subtitle: String? = null,
    val tags: List<String>,
    val title: String
)