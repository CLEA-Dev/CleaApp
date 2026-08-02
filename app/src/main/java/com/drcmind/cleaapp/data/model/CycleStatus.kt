package com.drcmind.cleaapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CycleStatus {
    @SerialName("ACTIVE") ACTIVE,
    @SerialName("COMPLETED") COMPLETED,
    @SerialName("CANCELLED") CANCELLED
}



