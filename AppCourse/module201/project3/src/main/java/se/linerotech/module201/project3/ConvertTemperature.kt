package se.linerotech.module201.project3

import kotlin.math.roundToInt

class ConvertTemperature {

    fun celsiusToFahrenheit(celsius: Double): Double {
        val convert = (celsius * NINE / FIVE + THIRTY_TWO)
        return formatDecimal(convert)
    }

    fun celsiusToKelvin(celsius: Double): Double {
        val convert = (celsius + KELVIN_VALUE)
        return formatDecimal(convert)
    }

    fun fahrenheitToCelsius(fahrenheit: Double): Double {
        val convert = ((fahrenheit - THIRTY_TWO) * FIVE / NINE)
        return formatDecimal(convert)
    }

    fun fahrenheitToKelvin(fahrenheit: Double): Double {
        val convert = ((fahrenheit + FAHRENHEIT_VALUE) * FIVE / NINE)
        return formatDecimal(convert)
    }

    fun kelvinToCelsius(temperature: Double): Double {
        val convert = (temperature - KELVIN_VALUE)
        return formatDecimal(convert)
    }

    fun kelvinToFahrenheit(temperature: Double): Double {
        val convert = formatDecimal((temperature * NINE / FIVE) - FAHRENHEIT_VALUE)
        return formatDecimal(convert)
    }

    private fun formatDecimal(number: Double): Double {
        return (number * DECIMAL).roundToInt() / DECIMAL
    }

    companion object {
        private const val DECIMAL = 100.0
        private const val KELVIN_VALUE = 273.15
        private const val NINE = 9.0
        private const val FIVE = 5.0
        private const val THIRTY_TWO = 32.0
        private const val FAHRENHEIT_VALUE = 459.67
    }
}
