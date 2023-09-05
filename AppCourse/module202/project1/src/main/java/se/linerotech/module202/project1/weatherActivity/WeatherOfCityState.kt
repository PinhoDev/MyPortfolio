package se.linerotech.module202.project1.weatherActivity

import se.linerotech.module202.project1.data.DataWeather

sealed class WeatherOfCityState {
    object Loading : WeatherOfCityState()
    data class Success(val details: DataWeather, val sunrise: String, val sunset: String) : WeatherOfCityState()
    object Failure : WeatherOfCityState()
}
