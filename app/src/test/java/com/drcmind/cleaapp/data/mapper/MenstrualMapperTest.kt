package com.drcmind.cleaapp.data.mapper

import com.drcmind.cleaapp.data.model.CycleStatus
import com.drcmind.cleaapp.data.model.FlowLevel
import com.drcmind.cleaapp.data.model.Mood
import com.drcmind.cleaapp.data.remote.dto.CycleDayDto
import com.drcmind.cleaapp.data.remote.dto.CycleDto
import com.drcmind.cleaapp.data.remote.dto.DashboardDto
import com.drcmind.cleaapp.data.remote.dto.PredictionDto
import com.drcmind.cleaapp.data.remote.dto.StatisticsDto
import com.drcmind.cleaapp.data.remote.dto.SymptomDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MenstrualMapperTest {

    private fun sampleCycleDto(
        status: CycleStatus = CycleStatus.ACTIVE,
        cycleDays: List<CycleDayDto> = emptyList()
    ) = CycleDto(
        id = "c1",
        startDate = "2026-08-01",
        endDate = "2026-08-29",
        cycleLength = 28,
        periodLength = 5,
        status = status,
        notes = "notes",
        cycleDays = cycleDays
    )

    @Test
    fun cycleDto_toDomain_mapsAllFields() {
        val domain = sampleCycleDto().toDomain()

        assertEquals("c1", domain.id)
        assertEquals("2026-08-01", domain.startDate)
        assertEquals("2026-08-29", domain.endDate)
        assertEquals(28, domain.cycleLength)
        assertEquals(5, domain.periodLength)
        assertEquals("ACTIVE", domain.status)
        assertEquals("notes", domain.notes)
        assertTrue(domain.cycleDays.isEmpty())
    }

    @Test
    fun cycleDto_toDomain_mapsStatusFromEnumName() {
        assertEquals("COMPLETED", sampleCycleDto(status = CycleStatus.COMPLETED).toDomain().status)
        assertEquals("CANCELLED", sampleCycleDto(status = CycleStatus.CANCELLED).toDomain().status)
    }

    @Test
    fun cycleDto_toEntity_persistsStatusAsName() {
        val entity = sampleCycleDto(status = CycleStatus.ACTIVE).toEntity()

        assertEquals("c1", entity.id)
        assertEquals("ACTIVE", entity.status)
        assertEquals("2026-08-01", entity.startDate)
        assertEquals(5, entity.periodLength)
    }

    @Test
    fun cycleDayDto_toDomain_mapsEnumsByName() {
        val dto = CycleDayDto(
            id = "d1",
            cycleId = "c1",
            date = "2026-08-01",
            flow = FlowLevel.HEAVY,
            painLevel = 3,
            mood = Mood.NEUTRAL,
            temperature = 36.6f,
            weight = 60.5f,
            medications = "paracetamol",
            notes = "ok",
            symptoms = emptyList()
        )

        val domain = dto.toDomain()

        assertEquals("d1", domain.id)
        assertEquals("HEAVY", domain.flow)
        assertEquals("NEUTRAL", domain.mood)
        assertEquals(36.6f, domain.temperature)
        assertEquals(60.5f, domain.weight)
        assertEquals("paracetamol", domain.medications)
        assertEquals(3, domain.painLevel)
    }

    @Test
    fun cycleDayDto_toDomain_mapsNullableMood() {
        val dto = CycleDayDto(
            id = "d1",
            cycleId = "c1",
            date = "2026-08-01",
            flow = FlowLevel.NONE,
            painLevel = null,
            mood = null,
            temperature = null,
            weight = null,
            medications = null,
            notes = null
        )

        val domain = dto.toDomain()

        assertNull(domain.mood)
        assertNull(domain.painLevel)
        assertNull(domain.temperature)
        assertNull(domain.medications)
    }

    @Test
    fun cycleDayDto_toEntity_keepsCycleId() {
        val dto = CycleDayDto(
            id = "d1",
            cycleId = "c1",
            date = "2026-08-01",
            flow = FlowLevel.LIGHT,
            painLevel = null,
            mood = null,
            temperature = null,
            weight = null,
            medications = null,
            notes = null
        )

        val entity = dto.toEntity()

        assertEquals("c1", entity.cycleId)
        assertEquals("LIGHT", entity.flow)
    }

    @Test
    fun symptomDto_toDomain_mapsAllFields() {
        val dto = SymptomDto(
            id = "s1",
            name = "Crampes",
            slug = "crampes",
            description = "Douleurs abdominales",
            icon = "cramps",
            isActive = true
        )

        val domain = dto.toDomain()

        assertEquals("s1", domain.id)
        assertEquals("Crampes", domain.name)
        assertEquals("crampes", domain.slug)
        assertEquals("Douleurs abdominales", domain.description)
        assertEquals("cramps", domain.icon)
    }

    @Test
    fun predictionDto_toDomain_mapsAllFields() {
        val dto = PredictionDto(
            averageCycleLength = 28,
            averagePeriodLength = 5,
            cycleVariation = 3,
            predictedPeriodStart = "2026-08-25",
            predictedPeriodEnd = "2026-08-30",
            predictedOvulation = "2026-08-14",
            fertilityStart = "2026-08-10",
            fertilityEnd = "2026-08-16",
            confidence = 0.85f
        )

        val domain = dto.toDomain()

        assertEquals(28, domain.averageCycleLength)
        assertEquals(5, domain.averagePeriodLength)
        assertEquals(3, domain.cycleVariation)
        assertEquals("2026-08-25", domain.predictedPeriodStart)
        assertEquals("2026-08-30", domain.predictedPeriodEnd)
        assertEquals("2026-08-14", domain.predictedOvulation)
        assertEquals("2026-08-10", domain.fertilityStart)
        assertEquals("2026-08-16", domain.fertilityEnd)
        assertEquals(0.85f, domain.confidence)
    }

    @Test
    fun dashboardDto_toDomain_mapsNestedObjects() {
        val stats = StatisticsDto(
            completedCyclesCount = 4,
            averageCycleLength = 29,
            averagePeriodLength = 5,
            cycleVariation = 2
        )
        val dto = DashboardDto(
            activeCycle = sampleCycleDto(),
            predictions = null,
            statistics = stats
        )

        val domain = dto.toDomain()

        assertEquals("c1", domain.activeCycle?.id)
        assertNull(domain.predictions)
        assertEquals(4, domain.stats.completedCyclesCount)
        assertEquals(29, domain.stats.averageCycleLength)
    }
}
