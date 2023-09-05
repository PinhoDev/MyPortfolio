package se.linerotech.module201.project3

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val convert = ConvertTemperature()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtonCelsius()
        setUpButtonFahrenheit()
        setUpButtonKelvin()
        hideKeyboard()
    }

    private fun setUpButtonCelsius() {
        binding.buttonCelsius.setOnClickListener {
            val celsius = getTemperature()

            val fahrenheit = convert.celsiusToFahrenheit(celsius)
            val kelvin = convert.celsiusToKelvin(celsius)

            updateUserTemperature(celsius, R.string.celsius_sign)
            showFirstConvert(fahrenheit, R.string.fahrenheit_sign)
            showSecondConvert(kelvin, R.string.kelvin_sign)

            hideKeyboard()
        }
    }

    private fun setUpButtonFahrenheit() {
        binding.buttonFahrenheit.setOnClickListener {
            val fahrenheit = getTemperature()

            val celsius = convert.fahrenheitToCelsius(fahrenheit)
            val kelvin = convert.fahrenheitToKelvin(fahrenheit)

            updateUserTemperature(fahrenheit, R.string.fahrenheit_sign)
            showFirstConvert(celsius, R.string.celsius_sign)
            showSecondConvert(kelvin, R.string.kelvin_sign)

            hideKeyboard()
        }
    }

    private fun setUpButtonKelvin() {
        binding.buttonKelvin.setOnClickListener {
            val kelvin = getTemperature()

            val celsius = convert.kelvinToCelsius(kelvin)
            val fahrenheit = convert.kelvinToFahrenheit(kelvin)

            updateUserTemperature(kelvin, R.string.kelvin_sign)
            showFirstConvert(celsius, R.string.celsius_sign)
            showSecondConvert(fahrenheit, R.string.fahrenheit_sign)

            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        binding.root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun temperatureIsValid(): Boolean {
        val value = binding.editTextTemperature.text.toString().trim()
        val pattern = ("^(?!.*\\.\\.).*$").toRegex()

        return if (!value.matches(pattern) && value.isEmpty()) {
            sendMessage(getString(R.string.invalid_temperature))
            false
        } else {
            true
        }
    }

    private fun sendMessage(message: String) {
        binding.editTextTemperature.error = message
    }

    private fun getTemperature(): Double {
        val temperature = binding.editTextTemperature.text.toString()
        val pattern = ("""(\d+(\.\d*)?)""").toRegex()

        return if (temperatureIsValid()) {
            pattern.find(temperature)?.value?.toDouble() ?: ZERO.toDouble()
        } else {
            return ZERO.toDouble()
        }
    }

    private fun updateUserTemperature(temperature: Double, sign: Int) {
        binding.editTextTemperature.setText(getString(sign, temperature))
    }

    private fun showFirstConvert(temperature: Double, sign: Int) {
        binding.textViewFirstTemperature.text = getString(sign, temperature)
    }

    private fun showSecondConvert(temperature: Double, sign: Int) {
        binding.textViewSecondTemperature.text = getString(sign, temperature)
    }

    companion object {
        private const val ZERO = 0
    }
}
