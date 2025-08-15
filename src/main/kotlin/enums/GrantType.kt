package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GrantType() {
    @SerialName("authorization_code") AUTHORIZATION_CODE,
    @SerialName("client_credentials") CLIENT_CREDENTIALS
}