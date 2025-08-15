package enums

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
enum class ModeEnum(val ruleset: String) {

    @SerialName("osu") OSU("osu"),
    @SerialName("mania") MANIA("mania"),
    @SerialName("fruits") FRUITS("fruits"),
    @SerialName("taiko") TAIKO("taiko")
}