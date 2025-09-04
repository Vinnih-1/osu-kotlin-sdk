package models.users

import enums.RelationType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class UserRelation(
    val targetId: Int,
    val relationType: RelationType? = null,
    val mutual: Boolean? = null,
    val target: UserCompact
)