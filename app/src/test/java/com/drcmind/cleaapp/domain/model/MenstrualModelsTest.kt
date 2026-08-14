package com.drcmind.cleaapp.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class MenstrualModelsTest {

    private fun cycle(startDate: String, status: String = "ACTIVE") = MenstrualCycle(
        id = "c1",
        startDate = startDate,
        endDate = null,
        cycleLength = 28,
        periodLength = 5,
        status = status,
        notes = null
    )

    private fun prediction(
        predictedOvulation: String,
        fertilityStart: String = "2026-08-10",
        fertilityEnd: String = "2026-08-16"
    ) = Prediction(
        averageCycleLength = 28,
        averagePeriodLength = 5,
        cycleVariation = 0,
        predictedPeriodStart = "2026-08-25",
        predictedPeriodEnd = "2026-08-30",
        predictedOvulation = predictedOvulation,
        fertilityStart = fertilityStart,
        fertilityEnd = fertilityEnd,
        confidence = 0.8f
    )

    private fun dashboard(
        active: MenstrualCycle?,
        predictions: Prediction? = null,
        periodLength: Int = 5
    ) = MenstrualDashboard(
        activeCycle = active,
        predictions = predictions,
        stats = MenstrualStats(completedCyclesCount = 1, averageCycleLength = 28, averagePeriodLength = periodLength, cycleVariation = 0)
    )

    @Test
    fun getCurrentDay_returnsOneOnStartDate() {
        val today = LocalDate.now().toString()
        assertEquals(1, cycle(today).getCurrentDay())
    }

    @Test
    fun getCurrentDay_neverReturnsZeroForPastStart() {
        val past = LocalDate.now().minusDays(3).toString()
        assert(getCurrentDaySafe(past) >= 1)
    }

    private fun getCurrentDaySafe(startDate: String): Int = cycle(startDate).getCurrentDay()

    @Test
    fun getPhaseName_returnsAnalysisWhenNoActiveCycle() {
        assertEquals("Analyse en cours", dashboard(active = null).getPhaseName())
    }

    @Test
    fun getPhaseName_menstrualOnFirstDays() {
        val today = LocalDate.now().toString()
        val dashboard = dashboard(
            active = cycle(today),
            predictions = prediction(predictedOvulation = LocalDate.now().plusDays(13).toString())
        )
        assertEquals("Phase Menstruelle", dashboard.getPhaseName())
    }

    @Test
    fun getPhaseName_follicularBetweenPeriodAndOvulation() {
        val startDate = LocalDate.now().minusDays(7).toString()
        val dashboard = dashboard(
            active = cycle(startDate),
            predictions = prediction(predictedOvulation = LocalDate.now().plusDays(13).toString())
        )
        assertEquals("Phase Folliculaire", dashboard.getPhaseName())
    }

    @Test
    fun getPhaseName_ovulationOnOvulationDay() {
        val today = LocalDate.now()
        val dashboard = dashboard(
            active = cycle(today.minusDays(13).toString()),
            predictions = prediction(predictedOvulation = today.toString())
        )
        assertEquals("Phase d'Ovulation", dashboard.getPhaseName())
    }

    @Test
    fun getPhaseName_lutealAfterOvulation() {
        val today = LocalDate.now()
        val dashboard = dashboard(
            active = cycle(today.minusDays(13).toString()),
            predictions = prediction(predictedOvulation = today.minusDays(2).toString())
        )
        assertEquals("Phase Lutéale", dashboard.getPhaseName())
    }

    @Test
    fun getFertilityRange_returnsInclusiveDates() {
        val prediction = prediction(
            predictedOvulation = "2026-08-14",
            fertilityStart = "2026-08-10",
            fertilityEnd = "2026-08-12"
        )
        assertEquals(
            listOf("2026-08-10", "2026-08-11", "2026-08-12"),
            prediction.getFertilityRange()
        )
    }

    @Test
    fun getFertilityRange_emptyWhenDatesInvalid() {
        val prediction = Prediction(
            averageCycleLength = 28,
            averagePeriodLength = 5,
            cycleVariation = 0,
            predictedPeriodStart = "2026-08-25",
            predictedPeriodEnd = "2026-08-30",
            predictedOvulation = "2026-08-14",
            fertilityStart = "not-a-date",
            fertilityEnd = "2026-08-12",
            confidence = 0.8f
        )
        assertEquals(emptyList<String>(), prediction.getFertilityRange())
    }
}
