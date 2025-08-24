package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GrantType(val value: String) {
    @SerialName("authorization_code") AUTHORIZATION_CODE("authorization_code"),
    @SerialName("client_credentials") CLIENT_CREDENTIALS("client_credentials")
}