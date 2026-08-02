package com.drcmind.cleaapp.data.local.room.dao

import androidx.room.*
import com.drcmind.cleaapp.data.local.room.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MenstrualDao {

    // --- Cycles ---
    @Query("SELECT * FROM cycles ORDER BY startDate DESC")
    fun getCyclesFlow(): Flow<List<CycleEntity>>

    @Query("SELECT * FROM cycles ORDER BY startDate DESC")
    suspend fun getAllCycles(): List<CycleEntity>

    @Query("SELECT * FROM cycles WHERE id = :id")
    suspend fun getCycleById(id: String): CycleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCycles(cycles: List<CycleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCycle(cycle: CycleEntity)

    @Query("DELETE FROM cycles WHERE id = :id")
    suspend fun deleteCycle(id: String)

    @Query("DELETE FROM cycles")
    suspend fun deleteAllCycles()

    // --- Days ---
    @Query("SELECT * FROM cycle_days WHERE cycleId = :cycleId ORDER BY date ASC")
    suspend fun getDaysForCycle(cycleId: String): List<DayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: DayEntity)

    @Query("DELETE FROM cycle_days WHERE id = :id")
    suspend fun deleteDay(id: String)

    // --- Symptoms ---
    @Query("SELECT * FROM symptoms")
    suspend fun getAllSymptoms(): List<SymptomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymptoms(symptoms: List<SymptomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaySymptomCrossRef(crossRef: List<DaySymptomCrossRef>)

    @Transaction
    @Query("""
        SELECT * FROM symptoms 
        INNER JOIN day_symptoms ON symptoms.id = day_symptoms.symptomId 
        WHERE day_symptoms.dayId = :dayId
    """)
    suspend fun getSymptomsForDay(dayId: String): List<SymptomEntity>
}
