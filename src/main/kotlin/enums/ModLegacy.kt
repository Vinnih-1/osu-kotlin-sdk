package enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ModLegacy {
    @SerialName("NF") NO_FAIL,
    @SerialName("EZ") EASY,
    @SerialName("HD") HIDDEN,
    @SerialName("HR") HARD_ROCK,
    @SerialName("SD") SUDDEN_DEATH,
    @SerialName("DT") DOUBLE_TIME,
    @SerialName("NC") NIGHTCORE,
    @SerialName("HT") HALF_TIME,
    @SerialName("FL") FLASHLIGHT,
    @SerialName("SO") SPUN_OUT,
    @SerialName("PF") PERFECT,
    @SerialName("RX") RELAX,
    @SerialName("AP") AUTOPILOT,
    @SerialName("SV2") SCORE_V2
}