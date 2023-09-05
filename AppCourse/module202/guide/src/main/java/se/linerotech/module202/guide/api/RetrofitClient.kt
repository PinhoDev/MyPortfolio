package se.linerotech.module202.guide.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val baseUrl = "https://newsapi.org/v2/"

    val instance: Endpoint by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
        retrofit.create(Endpoint::class.java)
    }
}
