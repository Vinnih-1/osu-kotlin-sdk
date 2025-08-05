package models

import kotlinx.serialization.Serializable

@Serializable
data class Build(
    val createdAt: String,
    val displayVersion: String,
    val id: Int,
    val updateStream: UpdateStream? = null,
    val users: Int,
    val version: String? = null,
    val youtubeId: String? = null,

    /**
     * Optional Attributes
     *
     * The following are attributes which may be
     * additionally included in responses. Relevant
     * endpoints should list them if applicable.
     */
    val changelogEntries: List<ChangelogEntry>? = null,
    val versions: Versions? = null
) {

    @Serializable
    data class Versions(
        val next: Build? = null,
        val previous: Build? = null
    )

    @Serializable
    data class ChangelogEntry(
        val category: String,
        val createdAt: String? = null,
        val githubPullRequestId: Int? = null,
        val githubUrl: String? = null,
        val id: Int? = null,
        val major: Boolean,
        val repository: String? = null,
        val title: String? = null,
        val type: String,
        val url: String? = null,

        /**
         * Optional Attributes
         *
         * The following are attributes which may be
         * additionally included in responses. Relevant
         * endpoints should list them if applicable.
         */
        val githubUser: GithubUser? = null,
        val message: String? = null,
        val messageHtml: String? = null
    ) {

        @Serializable
        data class GithubUser(
            val displayName: String,
            val githubUrl: String? = null,
            val githubUsername: String? = null,
            val id: Int? = null,
            val osuUsername: String? = null,
            val userId: Int? = null,
            val userUrl: String? = null
        )
    }

    @Serializable
    data class UpdateStream(
        val displayName: String? = null,
        val id: Int,
        val isFeature: Boolean? = null,
        val name: String,
        val latestBuild: Build? = null,
        val userCount: Int? = null
    )
}