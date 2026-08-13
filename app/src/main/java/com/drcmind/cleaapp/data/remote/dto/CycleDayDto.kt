package com.drcmind.cleaapp.data.remote.dto

import com.drcmind.cleaapp.data.model.FlowLevel
import com.drcmind.cleaapp.data.model.Mood
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CycleDayDto(
    val id: String = "",
    @SerialName("cycle_id") val cycleId: String = "",
    val date: String = "",
    val flow: FlowLevel = FlowLevel.MEDIUM,
    @SerialName("pain_level") val painLevel: Int? = null,
    val mood: Mood? = null,
    val temperature: Float? = null,
    val weight: Float? = null,
    val medications: String? = null,
    val notes: String? = null,
    val symptoms: List<SymptomDto> = emptyList()
)
