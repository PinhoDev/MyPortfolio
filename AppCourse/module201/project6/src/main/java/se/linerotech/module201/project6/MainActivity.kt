package se.linerotech.module201.project6

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project6.databinding.ActivityMainBinding
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextActionWeight()
        actionDoneHeight()
        setupNewUser()
        hideKeyboard()
        decimalSeparatorKeyboard()
    }

    private fun nextActionWeight() {
        binding.editTextWeight.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val weight = getUserWeight()
                if (isValidWeight(weight)) {
                    with(binding) {
                        editTextWeight.filters = arrayOf(InputFilter.LengthFilter(EIGHT))
                        editTextHeight.isEnabled = true
                        editTextHeight.requestFocus()
                        editTextWeight.isEnabled = false
                        editTextWeight.setText(
                            (getString(R.string.weight_sign, weight.toDouble()))
                        )
                    }
                }
            }
            false
        }
    }

    private fun isValidWeight(weight: String): Boolean {
        return when {
            weight.isEmpty() -> {
                binding.editTextWeight.error = getString(R.string.enter_your_weight)
                false
            }

            weight.matches(DOUBLE_DECIMAL) -> {
                binding.editTextWeight.error = (getString(R.string.invalid_weight))
                false
            }

            weight.matches(BEGIN_ZERO) -> {
                binding.editTextWeight.error = (getString(R.string.invalid_weight))
                false
            }

            else -> true
        }
    }

    private fun actionDoneHeight() {
        binding.editTextHeight.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val height = getUserHeight()
                if (isValidHeight(height)) {
                    with(binding) {
                        editTextHeight.filters = arrayOf(InputFilter.LengthFilter(EIGHT))
                        editTextHeight.isEnabled = false
                        editTextHeight.setText(
                            (getString(R.string.height_sign, height.toDouble()))
                        )
                    }
                    showBMIUser()
                    showBMICategory()
                }
            }
            false
        }
    }

    private fun isValidHeight(height: String): Boolean {
        return when {
            height.isEmpty() || height.first() == ZERO.toChar() -> {
                binding.editTextHeight.error = getString(R.string.enter_your_height)
                false
            }

            height.matches(DOUBLE_DECIMAL) -> {
                binding.editTextHeight.error = (getString(R.string.invalid_height))
                false
            }

            height.matches(BEGIN_ZERO) -> {
                binding.editTextHeight.error = (getString(R.string.invalid_height))
                false
            }

            else -> true
        }
    }

    private fun getUserWeight(): String {
        val weight = binding.editTextWeight.text.toString().trim()
        return parseUserInput(weight)
    }

    private fun getUserHeight(): String {
        val height = binding.editTextHeight.text.toString().trim()
        return parseUserInput(height)
    }

    private fun parseUserInput(input: String): String {
        return input.replace(COMMA, DOT)
    }

    private fun calculateBMI(): Double {
        val weight = VALID_VALUE.find(getUserWeight())?.value ?: EMPTY
        val height = VALID_VALUE.find(getUserHeight())?.value ?: EMPTY
        val heightConvertInM = height.toDouble() / ONE_HUNDRED

        return weight.toDouble() / heightConvertInM.pow(TWO)
    }

    private fun showBMIUser() {
        val bmi = calculateBMI()
        binding.textViewResult.text = (getString(R.string.bmi_sign, bmi))
    }

    private fun determineBMICategory(): String {
        val bmi = binding.textViewResult.text.toString().trim().toDouble()
        return when {
            bmi == ZERO.toDouble() -> getString(R.string.normal)
            bmi < UNDERWEIGHT_THRESHOLD -> getString(R.string.underweight)
            bmi < HEALTHY_WEIGHT_THRESHOLD -> getString(R.string.healthy_weight)
            bmi < OVERWEIGHT_THRESHOLD -> getString(R.string.overweight)
            else -> getString(R.string.obese)
        }
    }

    private fun showBMICategory() {
        binding.textViewCategory.text = determineBMICategory()
    }

    private fun setupNewUser() {
        binding.textViewNew.setOnClickListener {
            with(binding) {
                editTextWeight.isEnabled = true
                editTextWeight.setText(EMPTY)
                editTextWeight.hint = getString(R.string.your_weight)
                editTextHeight.isEnabled = true
                editTextHeight.setText(EMPTY)
                editTextHeight.hint = getString(R.string.your_height)
                textViewResult.text = getString(R.string.empty)
                textViewCategory.text = getString(R.string.normal)
                editTextHeight.isEnabled = false
            }
        }
    }

    private fun hideKeyboard() {
        binding.root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, ZERO)
        }
    }

    private fun decimalSeparatorKeyboard() {
        val symbols = DecimalFormatSymbols(
            Locale(
                getString(R.string.language_format),
                getString(R.string.country_format)
            )
        )

        with(binding) {
            editTextWeight.keyListener =
                DigitsKeyListener.getInstance(DIGITS + symbols.decimalSeparator)
            editTextHeight.keyListener =
                DigitsKeyListener.getInstance(DIGITS + symbols.decimalSeparator)
        }
    }

    companion object {
        private const val ZERO = 0
        private const val TWO = 2
        private const val EIGHT = 8
        private const val COMMA = ','
        private const val DOT = '.'
        private const val EMPTY = " "
        private const val DIGITS = "1234567890"
        private const val ONE_HUNDRED = 100
        private const val UNDERWEIGHT_THRESHOLD = 18.5
        private const val HEALTHY_WEIGHT_THRESHOLD = 25.0
        private const val OVERWEIGHT_THRESHOLD = 30.0
        private val BEGIN_ZERO = ("^0\\d*\$").toRegex()
        private val VALID_VALUE = ("-?\\d+(?:[.,]\\d+)?").toRegex()
        private val DOUBLE_DECIMAL = (".*[,.].*[,.].*").toRegex()
    }
}
