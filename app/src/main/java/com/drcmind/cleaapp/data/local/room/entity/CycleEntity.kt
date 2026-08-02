package com.drcmind.cleaapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycles")
data class CycleEntity(
    @PrimaryKey val id: String,
    val startDate: String,
    val endDate: String?,
    val cycleLength: Int?,
    val periodLength: Int?,
    val status: String,
    val notes: String?
)
