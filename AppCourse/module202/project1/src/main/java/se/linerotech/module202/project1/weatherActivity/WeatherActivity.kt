package se.linerotech.module202.project1.weatherActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import se.linerotech.module202.project1.R
import se.linerotech.module202.project1.data.DataWeather
import se.linerotech.module202.project1.databinding.LayoutWeatherActivityBinding
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: LayoutWeatherActivityBinding
    private val convert = ConvertTime()
    private val viewModelWeather: WeatherOfCityViewModel by viewModels {
        WeatherOfCityViewModel.factory(cityName())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutWeatherActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolBar(cityName())
        observeStateWeather()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configureToolBar(title: String) {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun cityName(): String {
        val city = intent.getIntExtra(KEY_CITY_NAME, 0)
        return getString(city)
    }

    private fun observeStateWeather() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelWeather.state.collect { state ->
                    when (state) {
                        WeatherOfCityState.Loading -> showMessage(R.string.loading)
                        is WeatherOfCityState.Success -> showWeatherOfCity(
                            state.details,
                            state.sunrise,
                            state.sunset
                        )

                        WeatherOfCityState.Failure -> showMessage(R.string.unable_retrieve)
                    }
                }
            }
        }
    }

    private fun showWeatherOfCity(details: DataWeather, sunriseTime: String, sunsetTime: String) {
        updateWeatherDetails(details)
        showImageWeather(details.icon)
        updateBackgroundResource(details.localDate!!, sunriseTime, sunsetTime)
    }

    private fun updateWeatherDetails(details: DataWeather) {
        with(binding) {
            main.text = details.main
            temperature.text = getString(R.string.celsius, details.temperature.toString())
            cloudiness.text = getString(R.string.percentage, details.cloudiness.toString())
            humidity.text = getString(R.string.percentage, details.humidity.toString())
            UVIndex.text = details.index.toString()
        }
    }

    private fun showImageWeather(icon: String?) {
        val iconUrl = BASE_URL.plus(icon)
        Glide.with(this)
            .load(iconUrl)
            .into(binding.image)
    }

    private fun updateBackgroundResource(
        localDate: String,
        sunriseTime: String,
        sunsetTime: String
    ) {
        val conditionText = binding.main.text.toString()
        val nightDay = dayIsNight(localDate, sunriseTime, sunsetTime)
        val resourceId = if (nightDay) {
            R.drawable.background_night
        } else {
            resourceIdForCondition(conditionText)
        }
        binding.background.setBackgroundResource(resourceId)
    }

    private fun resourceIdForCondition(conditionText: String): Int {
        return when {
            conditionText.containsAnyOf(
                getString(R.string.sunny),
                getString(R.string.unclouded)
            ) -> R.drawable.background_sunny

            conditionText.containsAnyOf(
                getString(R.string.cloudy),
                getString(R.string.overcast)
            ) -> R.drawable.background_cloudy

            conditionText.containsAnyOf(
                getString(R.string.rain),
                getString(R.string.drizzle)
            ) -> R.drawable.background_rainy

            conditionText.contains(
                getString(R.string.clear),
                ignoreCase = true
            ) -> R.drawable.background_clear

            else -> R.drawable.main_background
        }
    }

    private fun String.containsAnyOf(vararg substrings: String): Boolean {
        return substrings.any { this.contains(it, ignoreCase = true) }
    }

    private fun dayIsNight(localTime: String, sunriseTime: String, sunsetTime: String): Boolean {
        val localTimeAmPm = convert.convertAmToPm(localTime)
        val amPmFormat = SimpleDateFormat(AM_PM_Format, Locale.getDefault())

        val currentTimeFormat = amPmFormat.parse(localTimeAmPm)!!
        val sunriseFormat = amPmFormat.parse(sunriseTime)!!
        val sunsetFormat = amPmFormat.parse(sunsetTime)!!

        return currentTimeFormat > sunsetFormat || currentTimeFormat < sunriseFormat
    }

    private fun showMessage(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val AM_PM_Format = "hh:mm a"
        private const val BASE_URL = "https:"
        private const val KEY_CITY_NAME = "CITY_NAME"
        fun intent(context: Context, cityName: Int): Intent {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra(KEY_CITY_NAME, cityName)
            return intent
        }
    }
}
