package com.drcmind.cleaapp.data.remote.dto

import com.drcmind.cleaapp.data.model.CycleStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CycleDto(
    val id: String,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String?,
    @SerialName("cycle_length") val cycleLength: Int?,
    @SerialName("period_length") val periodLength: Int?,
    val status: CycleStatus,
    val notes: String?,
    @SerialName("cycle_days") val cycleDays: List<CycleDayDto> = emptyList()
)

