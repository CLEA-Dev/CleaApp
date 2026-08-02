package com.drcmind.cleaapp.data.repository

import com.drcmind.cleaapp.data.local.room.dao.MenstrualDao
import com.drcmind.cleaapp.data.mapper.toDomain
import com.drcmind.cleaapp.data.mapper.toEntity
import com.drcmind.cleaapp.data.remote.api.MenstrualApiService
import com.drcmind.cleaapp.domain.model.*
import com.drcmind.cleaapp.domain.repository.MenstrualRepository

class MenstrualRepositoryImpl(
    private val api: MenstrualApiService,
    private val dao: MenstrualDao
) : MenstrualRepository {

    // --- Cycles ---

    override suspend fun getCycles(): Result<List<MenstrualCycle>> = try {
        val remoteCycles = api.getCycles()
        dao.insertCycles(remoteCycles.map { it.toEntity() })
        Result.success(remoteCycles.map { it.toDomain() })
    } catch (e: Exception) {
        val localCycles = dao.getAllCycles()
        if (localCycles.isNotEmpty()) {
            Result.success(localCycles.map { it.toDomain() })
        } else {
            Result.failure(e)
        }
    }

    override suspend fun createCycle(startDate: String, notes: String?): Result<MenstrualCycle> = try {
        val cycle = api.createCycle(startDate, notes)
        dao.insertCycle(cycle.toEntity())
        Result.success(cycle.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCycle(id: String): Result<MenstrualCycle> = try {
        val cycleDto = api.getCycle(id)
        dao.insertCycle(cycleDto.toEntity())
        dao.insertDays(cycleDto.cycleDays.map { it.toEntity() })
        Result.success(cycleDto.toDomain())
    } catch (e: Exception) {
        val local = dao.getCycleById(id)
        if (local != null) {
            val days = dao.getDaysForCycle(id).map { it.toDomain() }
            Result.success(local.toDomain(days))
        } else Result.failure(e)
    }

    override suspend fun updateCycle(
        id: String,
        startDate: String?,
        endDate: String?,
        cycleLength: Int?,
        periodLength: Int?,
        status: String?,
        notes: String?
    ): Result<MenstrualCycle> = try {
        val params = mutableMapOf<String, String?>()
        startDate?.let { params["start_date"] = it }
        endDate?.let { params["end_date"] = it }
        cycleLength?.let { params["cycle_length"] = it.toString() }
        periodLength?.let { params["period_length"] = it.toString() }
        status?.let { params["status"] = it }
        notes?.let { params["notes"] = it }
        
        val updated = api.updateCycle(id, params)
        dao.insertCycle(updated.toEntity())
        Result.success(updated.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCycle(id: String): Result<Unit> = try {
        api.deleteCycle(id)
        dao.deleteCycle(id)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun completeCycle(
        id: String,
        endDate: String,
        cycleLength: Int,
        periodLength: Int
    ): Result<MenstrualCycle> = try {
        val completed = api.completeCycle(id, endDate, cycleLength, periodLength)
        dao.insertCycle(completed.toEntity())
        Result.success(completed.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    // --- Days ---

    override suspend fun getCycleDays(cycleId: String): Result<List<CycleDay>> = try {
        val remoteDays = api.getCycleDays(cycleId)
        dao.insertDays(remoteDays.map { it.toEntity() })
        Result.success(remoteDays.map { it.toDomain() })
    } catch (e: Exception) {
        val localDays = dao.getDaysForCycle(cycleId)
        Result.success(localDays.map { it.toDomain() })
    }

    override suspend fun addCycleDay(
        cycleId: String,
        date: String,
        flow: String,
        painLevel: Int?,
        mood: String?,
        temperature: Float?,
        weight: Float?,
        medications: String?,
        notes: String?,
        symptomIds: List<String>?
    ): Result<CycleDay> = try {
        val params = mutableMapOf<String, Any?>()
        params["date"] = date
        params["flow"] = flow
        painLevel?.let { params["pain_level"] = it }
        mood?.let { params["mood"] = it }
        temperature?.let { params["temperature"] = it }
        weight?.let { params["weight"] = it }
        medications?.let { params["medications"] = it }
        notes?.let { params["notes"] = it }
        symptomIds?.let { params["symptom_ids"] = it }

        val dayDto = api.addCycleDay(cycleId, params)
        dao.insertDay(dayDto.toEntity())
        Result.success(dayDto.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCycleDay(
        cycleId: String,
        dayId: String,
        flow: String?,
        painLevel: Int?,
        mood: String?,
        temperature: Float?,
        weight: Float?,
        medications: String?,
        notes: String?
    ): Result<CycleDay> = try {
        val params = mutableMapOf<String, Any?>()
        flow?.let { params["flow"] = it }
        painLevel?.let { params["pain_level"] = it }
        mood?.let { params["mood"] = it }
        temperature?.let { params["temperature"] = it }
        weight?.let { params["weight"] = it }
        medications?.let { params["medications"] = it }
        notes?.let { params["notes"] = it }

        val updated = api.updateCycleDay(cycleId, dayId, params)
        dao.insertDay(updated.toEntity())
        Result.success(updated.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCycleDay(cycleId: String, dayId: String): Result<Unit> = try {
        api.deleteCycleDay(cycleId, dayId)
        dao.deleteDay(dayId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun attachSymptomsToDay(
        cycleId: String,
        dayId: String,
        symptoms: List<Pair<String, Int>>
    ): Result<Unit> = try {
        val symptomParams = symptoms.map { mapOf("id" to it.first, "severity" to it.second) }
        api.attachSymptomsToDay(cycleId, dayId, symptomParams)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // --- Symptoms ---

    override suspend fun getSymptoms(): Result<List<Symptom>> = try {
        val remote = api.getSymptoms()
        dao.insertSymptoms(remote.map { it.toEntity() })
        Result.success(remote.map { it.toDomain() })
    } catch (e: Exception) {
        val local = dao.getAllSymptoms()
        Result.success(local.map { it.toDomain() })
    }

    // --- Predictions ---

    override suspend fun getPredictions(): Result<Prediction> = try {
        Result.success(api.getPredictions().toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    // --- Dashboard ---

    override suspend fun getDashboard(): Result<MenstrualDashboard> = try {
        val remote = api.getDashboard()
        remote.activeCycle?.let { dao.insertCycle(it.toEntity()) }
        Result.success(remote.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
