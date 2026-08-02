package com.drcmind.cleaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Mood {
    @SerialName("VERY_GOOD") VERY_GOOD,
    @SerialName("GOOD") GOOD,
    @SerialName("NEUTRAL") NEUTRAL,
    @SerialName("BAD") BAD,
    @SerialName("VERY_BAD") VERY_BAD
}
