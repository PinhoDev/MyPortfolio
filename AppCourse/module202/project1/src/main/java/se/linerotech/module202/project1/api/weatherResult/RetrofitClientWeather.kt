package se.linerotech.module202.project1.api.weatherResult

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClientWeather {
    private const val baseUrl = "https://api.weatherapi.com/v1/"
    val instance: EndpointWeather by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
        retrofit.create(EndpointWeather::class.java)
    }
}
