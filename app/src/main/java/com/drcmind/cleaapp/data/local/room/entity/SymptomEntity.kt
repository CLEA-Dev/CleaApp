package com.drcmind.cleaapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "symptoms")
data class SymptomEntity(
    @PrimaryKey val id: String,
    val name: String,
    val slug: String,
    val description: String?,
    val icon: String?
)

@Entity(
    tableName = "day_symptoms",
    primaryKeys = ["dayId", "symptomId"],
    foreignKeys = [
        ForeignKey(
            entity = DayEntity::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SymptomEntity::class,
            parentColumns = ["id"],
            childColumns = ["symptomId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["dayId"]), Index(value = ["symptomId"])]
)
data class DaySymptomCrossRef(
    val dayId: String,
    val symptomId: String,
    val severity: Int = 1
)
