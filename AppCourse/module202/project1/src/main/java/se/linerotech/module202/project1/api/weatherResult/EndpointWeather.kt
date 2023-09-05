package se.linerotech.module202.project1.api.weatherResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EndpointWeather {

    @GET("current.json")
    fun endPointWeather(
        @Query("Key") apiKey: String,
        @Query("q") city: String,
    ): Call<WeatherResultDTO>
}
