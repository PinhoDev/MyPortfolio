package se.linerotech.module202.project1.api.dayBreakResult

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Astronomy(
    @Json(name = "astro")
    val astro: Astro? = Astro()
)
