package com.drcmind.cleaapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class SymptomEntity(
    @PrimaryKey val id: String,
    val name: String,
    val slug: String,
    val description: String?,
    val icon: String?
)
