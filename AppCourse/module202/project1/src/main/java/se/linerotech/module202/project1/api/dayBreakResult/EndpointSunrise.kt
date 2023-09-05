package se.linerotech.module202.project1.api.dayBreakResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EndpointSunrise {

    @GET("astronomy.json")
    fun endpointSunrise(
        @Query("Key") apiKey: String,
        @Query("q") city: String,
    ): Call<DayBreakResultDTO>
}
