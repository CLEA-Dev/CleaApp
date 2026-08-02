package com.drcmind.cleaapp.domain.repository

import com.drcmind.cleaapp.domain.model.*

interface MenstrualRepository {
    // Cycles
    suspend fun getCycles(): Result<List<MenstrualCycle>>
    suspend fun createCycle(startDate: String, notes: String?): Result<MenstrualCycle>
    suspend fun getCycle(id: String): Result<MenstrualCycle>
    suspend fun updateCycle(id: String, startDate: String?, endDate: String?, cycleLength: Int?, periodLength: Int?, status: String?, notes: String?): Result<MenstrualCycle>
    suspend fun deleteCycle(id: String): Result<Unit>
    suspend fun completeCycle(id: String, endDate: String, cycleLength: Int, periodLength: Int): Result<MenstrualCycle>

    // Days
    suspend fun getCycleDays(cycleId: String): Result<List<CycleDay>>
    suspend fun addCycleDay(cycleId: String, date: String, flow: String, painLevel: Int?, mood: String?, temperature: Float?, weight: Float?, medications: String?, notes: String?, symptomIds: List<String>?): Result<CycleDay>
    suspend fun updateCycleDay(cycleId: String, dayId: String, flow: String?, painLevel: Int?, mood: String?, temperature: Float?, weight: Float?, medications: String?, notes: String?): Result<CycleDay>
    suspend fun deleteCycleDay(cycleId: String, dayId: String): Result<Unit>
    suspend fun attachSymptomsToDay(cycleId: String, dayId: String, symptoms: List<Pair<String, Int>>): Result<Unit>

    // Symptoms
    suspend fun getSymptoms(): Result<List<Symptom>>

    // Predictions
    suspend fun getPredictions(): Result<Prediction>

    // Dashboard
    suspend fun getDashboard(): Result<MenstrualDashboard>
}
