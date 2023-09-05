package se.linerotech.module202.project1.api.weatherResult

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResultDTO(
    @Json(name = "current")
    val current: Current? = null,
    @Json(name = "location")
    val location: Location? = null
)
