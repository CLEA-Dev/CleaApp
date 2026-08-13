package com.drcmind.cleaapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDto(
    @SerialName("completed_cycles_count") val completedCyclesCount: Int = 0,
    @SerialName("average_cycle_length") val averageCycleLength: Int = 28,
    @SerialName("average_period_length") val averagePeriodLength: Int = 5,
    @SerialName("cycle_variation") val cycleVariation: Int = 0
)
