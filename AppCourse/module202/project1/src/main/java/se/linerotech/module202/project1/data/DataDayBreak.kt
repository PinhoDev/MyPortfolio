package se.linerotech.module202.project1.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import se.linerotech.module202.project1.api.dayBreakResult.DayBreakResultDTO

@Parcelize
data class DataDayBreak(
    val sunrise: String?,
    val sunset: String?
) : Parcelable {
    constructor(dto: DayBreakResultDTO) : this(
        sunrise = dto.astronomy?.astro?.sunrise ?: EMPTY,
        sunset = dto.astronomy?.astro?.sunset ?: EMPTY
    )

    companion object {
        private const val EMPTY = " "
    }
}
