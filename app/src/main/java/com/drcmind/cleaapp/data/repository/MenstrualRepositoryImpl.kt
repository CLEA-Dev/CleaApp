package com.drcmind.cleaapp.data.repository

import com.drcmind.cleaapp.data.local.room.dao.MenstrualDao
import com.drcmind.cleaapp.data.mapper.toDomain
import com.drcmind.cleaapp.data.mapper.toEntity
import com.drcmind.cleaapp.data.model.CycleStatus
import com.drcmind.cleaapp.data.remote.api.MenstrualApiService
import com.drcmind.cleaapp.domain.model.*
import com.drcmind.cleaapp.domain.repository.MenstrualRepository

class MenstrualRepositoryImpl(
    private val api: MenstrualApiService,
    private val dao: MenstrualDao
) : MenstrualRepository {

    override suspend fun getCycles(): Result<List<MenstrualCycle>> = try {
        val remote = api.getCycles()
        dao.insertCycles(remote.map { it.toEntity() })
        Result.success(remote.map { it.toDomain() })
    } catch (e: Exception) {
        val local = dao.getAllCycles()
        if (local.isNotEmpty()) Result.success(local.map { it.toDomain() }) else Result.failure(e)
    }

    override suspend fun createCycle(startDate: String, notes: String?): Result<MenstrualCycle> = try {
        val cycle = api.createCycle(startDate, notes)
        dao.insertCycle(cycle.toEntity())
        Result.success(cycle.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCycle(id: String): Result<MenstrualCycle> = try {
        val dto = api.getCycle(id)
        dao.insertCycle(dto.toEntity())
        dao.insertDays(dto.cycleDays.map { it.toEntity() })
        Result.success(dto.toDomain())
    } catch (e: Exception) {
        dao.getCycleById(id)?.let { local ->
            val days = dao.getDaysForCycle(id).map { it.toDomain() }
            Result.success(local.toDomain(days))
        } ?: Result.failure(e)
    }

    override suspend fun updateCycle(id: String, startDate: String?, endDate: String?, cycleLength: Int?, periodLength: Int?, status: String?, notes: String?): Result<MenstrualCycle> = try {
        val params = mutableMapOf<String, String?>().apply {
            startDate?.let { put("start_date", it) }
            endDate?.let { put("end_date", it) }
            cycleLength?.let { put("cycle_length", it.toString()) }
            periodLength?.let { put("period_length", it.toString()) }
            status?.let { put("status", it) }
            notes?.let { put("notes", it) }
        }
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

    override suspend fun completeCycle(id: String, endDate: String, cycleLength: Int, periodLength: Int): Result<MenstrualCycle> = try {
        val completed = api.completeCycle(id, endDate, cycleLength, periodLength)
        dao.insertCycle(completed.toEntity())
        Result.success(completed.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCycleDays(cycleId: String): Result<List<CycleDay>> = try {
        val remote = api.getCycleDays(cycleId)
        dao.insertDays(remote.map { it.toEntity() })
        Result.success(remote.map { it.toDomain() })
    } catch (e: Exception) {
        Result.success(dao.getDaysForCycle(cycleId).map { it.toDomain() })
    }

    override suspend fun addCycleDay(cycleId: String, date: String, flow: String, painLevel: Int?, mood: String?, temperature: Float?, weight: Float?, medications: String?, notes: String?, symptomIds: List<String>?): Result<CycleDay> = try {
        val params = mutableMapOf<String, Any?>().apply {
            put("date", date)
            put("flow", flow)
            painLevel?.let { put("pain_level", it) }
            mood?.let { put("mood", it) }
            temperature?.let { put("temperature", it) }
            weight?.let { put("weight", it) }
            medications?.let { put("medications", it) }
            notes?.let { put("notes", it) }
            symptomIds?.let { put("symptom_ids", it) }
        }
        val day = api.addCycleDay(cycleId, params)
        dao.insertDay(day.toEntity())
        Result.success(day.toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCycleDay(cycleId: String, dayId: String, flow: String?, painLevel: Int?, mood: String?, temperature: Float?, weight: Float?, medications: String?, notes: String?): Result<CycleDay> = try {
        val params = mutableMapOf<String, Any?>().apply {
            flow?.let { put("flow", it) }
            painLevel?.let { put("pain_level", it) }
            mood?.let { put("mood", it) }
            temperature?.let { put("temperature", it) }
            weight?.let { put("weight", it) }
            medications?.let { put("medications", it) }
            notes?.let { put("notes", it) }
        }
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

    override suspend fun attachSymptomsToDay(cycleId: String, dayId: String, symptoms: List<Pair<String, Int>>): Result<Unit> = try {
        val params = symptoms.map { mapOf("id" to it.first, "severity" to it.second) }
        api.attachSymptomsToDay(cycleId, dayId, params)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getSymptoms(): Result<List<Symptom>> = try {
        val remote = api.getSymptoms()
        dao.insertSymptoms(remote.map { it.toEntity() })
        Result.success(remote.map { it.toDomain() })
    } catch (e: Exception) {
        Result.success(dao.getAllSymptoms().map { it.toDomain() })
    }

    override suspend fun getPredictions(): Result<Prediction> = try {
        Result.success(api.getPredictions().toDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getDashboard(): Result<MenstrualDashboard> = try {
        val remote = api.getDashboard()
        remote.activeCycle?.let { dao.insertCycle(it.toEntity()) }
        remote.activeCycle?.cycleDays?.map { it.toEntity() }?.let { dao.insertDays(it) }
        Result.success(remote.toDomain())
    } catch (e: Exception) {
        // Offline fallback: construire dashboard depuis le cache local
        val activeCycleEntity = dao.getActiveCycle(CycleStatus.ACTIVE)
        val activeCycle = activeCycleEntity?.let { cycle ->
            val days = dao.getActiveCycleDays(CycleStatus.ACTIVE)
            cycle.toDomain(days.map { it.toDomain() })
        }
        val completedCycles = dao.getCompletedCycles(CycleStatus.COMPLETED)
        val stats = if (completedCycles.isNotEmpty()) {
            val lengths = completedCycles.mapNotNull { it.cycleLength }.filter { it > 0 }
            val periodLengths = completedCycles.mapNotNull { it.periodLength }.filter { it > 0 }
            MenstrualStats(
                completedCyclesCount = completedCycles.size,
                averageCycleLength = if (lengths.isNotEmpty()) lengths.average().toInt() else 28,
                averagePeriodLength = if (periodLengths.isNotEmpty()) periodLengths.average().toInt() else 5,
                cycleVariation = if (lengths.size > 1) (lengths.maxOrNull()!! - lengths.minOrNull()!!) else 0
            )
        } else {
            MenstrualStats(0, 28, 5, 0)
        }
        Result.success(MenstrualDashboard(
            activeCycle = activeCycle,
            predictions = null, // Prédictions non disponibles hors-ligne
            stats = stats
        ))
    }
}
