package com.drcmind.cleaapp.data.remote.dto

import com.drcmind.cleaapp.data.model.FlowLevel
import com.drcmind.cleaapp.data.model.Mood
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CycleDayDto(
    val id: String,
    @SerialName("cycle_id") val cycleId: String,
    val date: String,
    val flow: FlowLevel,
    @SerialName("pain_level") val painLevel: Int?,
    val mood: Mood?,
    val temperature: Float?,
    val weight: Float?,
    val medications: String?,
    val notes: String?,
    val symptoms: List<SymptomDto> = emptyList()
)
