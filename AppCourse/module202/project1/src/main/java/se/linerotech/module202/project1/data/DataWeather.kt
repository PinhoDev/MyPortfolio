package se.linerotech.module202.project1.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import se.linerotech.module202.project1.api.weatherResult.WeatherResultDTO

@Parcelize
data class DataWeather(

    val main: String?,
    val temperature: Double?,
    val cloudiness: Int?,
    val humidity: Int?,
    val index: Double?,
    val localDate: String?,
    val icon: String?,

) : Parcelable {
    constructor(dto: WeatherResultDTO) : this(
        main = dto.current?.condition?.text ?: EMPTY,
        temperature = dto.current?.tempC ?: DOUBLE,
        cloudiness = dto.current?.cloud ?: ZERO,
        humidity = dto.current?.humidity ?: ZERO,
        index = dto.current?.uv ?: DOUBLE,
        localDate = dto.location?.localtime ?: EMPTY,
        icon = dto.current?.condition?.icon ?: EMPTY
    )

    companion object {
        private const val EMPTY = " "
        private const val ZERO = 0
        private const val DOUBLE = 0.0
    }
}
