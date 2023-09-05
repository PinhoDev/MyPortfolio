package se.linerotech.module202.project1.api.dayBreakResult

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClientSunrise {
    private const val baseUrl = "https://api.weatherapi.com/v1/"
    val instance: EndpointSunrise by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
        retrofit.create(EndpointSunrise::class.java)
    }
}
