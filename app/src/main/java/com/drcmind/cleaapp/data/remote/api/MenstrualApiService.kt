package com.drcmind.cleaapp.data.remote.api

import com.drcmind.cleaapp.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

class MenstrualApiService(private val client: HttpClient) {

    // --- Cycles ---
    suspend fun getCycles(): List<CycleDto> = client.get("api/cycles").body()

    suspend fun createCycle(startDate: String, notes: String?): CycleDto = client.post("api/cycles") {
        contentType(ContentType.Application.Json)
        setBody(mapOf("start_date" to startDate, "notes" to notes))
    }.body()

    suspend fun getCycle(id: String): CycleDto = client.get("api/cycles/$id").body()

    suspend fun updateCycle(id: String, params: Map<String, String?>): CycleDto = client.put("api/cycles/$id") {
        contentType(ContentType.Application.Json)
        setBody(params)
    }.body()

    suspend fun deleteCycle(id: String): HttpResponse = client.delete("api/cycles/$id")

    suspend fun completeCycle(id: String, endDate: String, cycleLength: Int, periodLength: Int): CycleDto = 
        client.post("api/cycles/$id/complete") {
            contentType(ContentType.Application.Json)
            setBody(mapOf(
                "end_date" to endDate,
                "cycle_length" to cycleLength.toString(),
                "period_length" to periodLength.toString()
            ))
        }.body()

    // --- Cycle Days ---
    suspend fun getCycleDays(cycleId: String): List<CycleDayDto> = client.get("api/cycles/$cycleId/days").body()

    suspend fun addCycleDay(cycleId: String, params: Map<String, Any?>): CycleDayDto = client.post("api/cycles/$cycleId/days") {
        contentType(ContentType.Application.Json)
        setBody(params)
    }.body()

    suspend fun updateCycleDay(cycleId: String, dayId: String, params: Map<String, Any?>): CycleDayDto = 
        client.put("api/cycles/$cycleId/days/$dayId") {
            contentType(ContentType.Application.Json)
            setBody(params)
        }.body()

    suspend fun deleteCycleDay(cycleId: String, dayId: String): HttpResponse = client.delete("api/cycles/$cycleId/days/$dayId")

    suspend fun attachSymptomsToDay(cycleId: String, dayId: String, symptoms: List<Map<String, Any>>): HttpResponse =
        client.post("api/cycles/$cycleId/days/$dayId/symptoms") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("symptoms" to symptoms))
        }

    // --- Symptoms ---
    suspend fun getSymptoms(): List<SymptomDto> = client.get("api/symptoms").body()

    // --- Predictions ---
    suspend fun getPredictions(): PredictionDto = client.get("api/predictions").body()

    // --- Dashboard ---
    suspend fun getDashboard(): DashboardDto = client.get("api/dashboard/cycle").body()
}
