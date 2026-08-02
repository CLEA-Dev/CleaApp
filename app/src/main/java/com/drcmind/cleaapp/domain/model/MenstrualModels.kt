package com.drcmind.cleaapp.domain.model

data class MenstrualCycle(
    val id: String,
    val startDate: String,
    val endDate: String?,
    val cycleLength: Int?,
    val periodLength: Int?,
    val status: String,
    val notes: String?,
    val cycleDays: List<CycleDay> = emptyList()
)

data class CycleDay(
    val id: String,
    val date: String,
    val flow: String,
    val painLevel: Int?,
    val mood: String?,
    val temperature: Float?,
    val weight: Float?,
    val medications: String?,
    val notes: String?,
    val symptoms: List<Symptom> = emptyList()
)

data class Symptom(
    val id: String,
    val name: String,
    val slug: String,
    val description: String?,
    val icon: String?
)

data class Prediction(
    val averageCycleLength: Int,
    val averagePeriodLength: Int,
    val cycleVariation: Int,
    val predictedPeriodStart: String,
    val predictedPeriodEnd: String,
    val predictedOvulation: String,
    val fertilityStart: String,
    val fertilityEnd: String,
    val confidence: Float
)

data class MenstrualDashboard(
    val activeCycle: MenstrualCycle?,
    val predictions: Prediction?,
    val stats: MenstrualStats
)

data class MenstrualStats(
    val completedCyclesCount: Int,
    val averageCycleLength: Int,
    val averagePeriodLength: Int,
    val cycleVariation: Int
)
