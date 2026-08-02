package com.drcmind.cleaapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDto(
    @SerialName("completed_cycles_count") val completedCyclesCount: Int,
    @SerialName("average_cycle_length") val averageCycleLength: Int,
    @SerialName("average_period_length") val averagePeriodLength: Int,
    @SerialName("cycle_variation") val cycleVariation: Int
)
