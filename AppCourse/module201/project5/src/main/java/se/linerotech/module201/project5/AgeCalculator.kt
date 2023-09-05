package se.linerotech.module201.project5

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

class AgeCalculator(birthYear: Int, birthMonth: Int) {
    private val dateOfBirth = LocalDate.of(birthYear, birthMonth, ONE)
    private fun currentDate(): LocalDate = LocalDate.now()

    fun ageInYears(): Int {
        val period = Period.between(dateOfBirth, currentDate())
        return period.years
    }

    fun ageInMonths(): Int {
        val period = Period.between(dateOfBirth, currentDate())
        return (ageInYears() * MONTHS_IN_YEAR) + period.months
    }

    fun ageInWeeks(): Long {
        return ChronoUnit.WEEKS.between(dateOfBirth, currentDate())
    }

    fun ageInDays(): Long {
        return ChronoUnit.DAYS.between(dateOfBirth, currentDate())
    }

    fun ageInHours(): Long {
        return ChronoUnit.HOURS.between(dateOfBirth.atStartOfDay(), currentDate().atStartOfDay())
    }

    fun ageInMinutes(): Long {
        return ChronoUnit.MINUTES.between(dateOfBirth.atStartOfDay(), currentDate().atStartOfDay())
    }

    companion object {
        private const val ONE = 1
        private const val MONTHS_IN_YEAR = 12
    }
}
