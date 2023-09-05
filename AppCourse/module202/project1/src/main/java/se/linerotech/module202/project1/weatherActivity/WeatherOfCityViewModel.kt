package se.linerotech.module202.project1.weatherActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import se.linerotech.module202.project1.api.Credentials
import se.linerotech.module202.project1.api.dayBreakResult.DayBreakResultDTO
import se.linerotech.module202.project1.api.dayBreakResult.RetrofitClientSunrise
import se.linerotech.module202.project1.api.weatherResult.RetrofitClientWeather
import se.linerotech.module202.project1.api.weatherResult.WeatherResultDTO
import se.linerotech.module202.project1.data.DataWeather

class WeatherOfCityViewModel(private val city: String) : ViewModel() {
    private val _state = MutableStateFlow<WeatherOfCityState>(WeatherOfCityState.Loading)
    val state: StateFlow<WeatherOfCityState> = _state

    init {
        retrieveWeather()
    }

    private fun retrieveWeather() {
        headLinesWeather().enqueue(object : Callback<WeatherResultDTO> {
            override fun onResponse(
                call: Call<WeatherResultDTO>,
                response: Response<WeatherResultDTO>
            ) {
                val details: WeatherResultDTO? = response.body()
                if (details != null) {
                    val weatherDetails = DataWeather(details)
                    retrieveSunrise(weatherDetails)
                } else {
                    _state.value = WeatherOfCityState.Failure
                }
            }

            override fun onFailure(call: Call<WeatherResultDTO>, t: Throwable) {
                _state.value = WeatherOfCityState.Failure
            }
        })
    }

    private fun headLinesWeather(): Call<WeatherResultDTO> {
        return RetrofitClientWeather.instance.endPointWeather(
            city = city,
            apiKey = Credentials.apiKey,
        )
    }

    private fun retrieveSunrise(weatherDetails: DataWeather) {
        headLinesSunrise().enqueue(object : Callback<DayBreakResultDTO> {
            override fun onResponse(
                call: Call<DayBreakResultDTO>,
                response: Response<DayBreakResultDTO>
            ) {
                val details: DayBreakResultDTO? = response.body()
                val sunriseTime = details?.astronomy?.astro?.sunrise
                val sunsetTime = details?.astronomy?.astro?.sunset

                _state.value = if (sunriseTime != null && sunsetTime != null) {
                    WeatherOfCityState.Success(weatherDetails, sunriseTime, sunsetTime)
                } else {
                    WeatherOfCityState.Failure
                }
            }

            override fun onFailure(call: Call<DayBreakResultDTO>, t: Throwable) {
                _state.value = WeatherOfCityState.Failure
            }
        })
    }

    private fun headLinesSunrise(): Call<DayBreakResultDTO> {
        return RetrofitClientSunrise.instance.endpointSunrise(
            city = city,
            apiKey = Credentials.apiKey,
        )
    }

    companion object {
        fun factory(city: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WeatherOfCityViewModel(city) as T
                }
            }
        }
    }
}
