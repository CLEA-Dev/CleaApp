package com.drcmind.cleaapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardDto(
    @SerialName("active_cycle") val activeCycle: CycleDto?,
    val predictions: PredictionDto?,
    val statistics: StatisticsDto
)
