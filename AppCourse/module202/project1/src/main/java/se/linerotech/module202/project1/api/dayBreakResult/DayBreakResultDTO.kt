package se.linerotech.module202.project1.api.dayBreakResult

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayBreakResultDTO(
    @Json(name = "astronomy")
    val astronomy: Astronomy? = Astronomy(),
    @Json(name = "location")
    val location: Location? = Location()
)
