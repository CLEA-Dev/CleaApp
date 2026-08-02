package com.drcmind.cleaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FlowLevel {
    @SerialName("NONE") NONE,
    @SerialName("SPOTTING") SPOTTING,
    @SerialName("LIGHT") LIGHT,
    @SerialName("MEDIUM") MEDIUM,
    @SerialName("HEAVY") HEAVY
}