package com.drcmind.cleaapp.data.remote.dto// Fichier : data/remote/dto/MenstrualDtos.kt
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SymptomDto(
    val id: String,
    val name: String,
    val slug: String,
    val description: String?,
    val icon: String?,
    @SerialName("is_active") val isActive: Boolean
)

