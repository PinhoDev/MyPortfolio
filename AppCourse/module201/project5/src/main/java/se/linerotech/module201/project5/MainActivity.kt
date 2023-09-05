package se.linerotech.module201.project5

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project5.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextActionYear()
        actionDoneMonth()
        hideKeyboard()
        setupNewUser()
    }

    private fun nextActionYear() {
        binding.editTextYear.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if (isValidYear(getYearOfBirth())) {
                    with(binding) {
                        editTextYear.filters = arrayOf(InputFilter.LengthFilter(EIGHTEEN))
                        editTextMonth.isEnabled = true
                        editTextMonth.requestFocus()
                        editTextYear.setText(getString(R.string.year_sign, getYearOfBirth()))
                    }
                }
            }
            false
        }
    }

    private fun isValidYear(years: String): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return when {
            years.isEmpty() -> {
                binding.editTextYear.error = getString(R.string.enter_year_of_birth)
                false
            }

            years.toInt() < (currentYear - MAX_AGE) -> {
                binding.editTextYear.error = getString(R.string.invalid_to_calculate)
                false
            }

            years.toInt() > currentYear -> {
                binding.editTextYear.error = getString(R.string.invalid_year_of_birth)
                false
            }

            else -> true
        }
    }

    private fun actionDoneMonth() {
        binding.editTextMonth.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isValidMonth(getMonthOfBirth())) {
                    with(binding) {
                        editTextMonth.filters = arrayOf(InputFilter.LengthFilter(EIGHTEEN))
                        editTextMonth.isEnabled = true
                        editTextYear.isEnabled = false
                        editTextMonth.isEnabled = false
                        editTextMonth.setText(getString(R.string.month_sign, getMonthOfBirth()))
                    }
                    showAgeUser()
                }
            }
            false
        }
    }

    private fun isValidMonth(month: String): Boolean {
        return when {
            month.isEmpty() -> {
                binding.editTextMonth.error = getString(R.string.enter_month_of_birth)
                false
            }

            month.toInt() < ONE || month.toInt() > TWELVE -> {
                binding.editTextMonth.error = getString(R.string.invalid_month_of_birth)
                false
            }

            else -> true
        }
    }

    private fun getYearOfBirth(): String {
        val yearOfBirth = binding.editTextYear.text.toString().trim()
        return PATTERN_DIGITS.find(yearOfBirth)?.value ?: EMPTY
    }

    private fun getMonthOfBirth(): String {
        val monthOfBirth = binding.editTextMonth.text.toString().trim()
        return PATTERN_DIGITS.find(monthOfBirth)?.value ?: EMPTY
    }

    private fun showAgeUser() {
        val yearOfBirth = getYearOfBirth().toInt()
        val yearMonth = getMonthOfBirth().toInt()

        val calculate = AgeCalculator(yearOfBirth, yearMonth)

        val ageInYears = calculate.ageInYears()
        val ageInMonths = calculate.ageInMonths()
        val ageInWeeks = calculate.ageInWeeks()
        val ageInDays = calculate.ageInDays()
        val ageInHours = calculate.ageInHours()
        val ageInMinutes = calculate.ageInMinutes()

        val numberFormat = NumberFormat.getInstance(Locale.getDefault())

        with(binding) {
            numberOfYear.text = numberFormat.format(ageInYears)
            numberOfMonths.text = numberFormat.format(ageInMonths)
            numberOfWeeks.text = numberFormat.format(ageInWeeks)
            numberOfDays.text = numberFormat.format(ageInDays)
            numberOfHours.text = numberFormat.format(ageInHours)
            numberOfMinutes.text = numberFormat.format(ageInMinutes)
        }
    }

    private fun setupNewUser() {
        binding.textViewNew.setOnClickListener {
            with(binding) {
                editTextYear.isEnabled = true
                editTextYear.setText(EMPTY)
                editTextYear.hint = getString(R.string.year_of_birth)
                editTextMonth.isEnabled = true
                editTextMonth.setText(EMPTY)
                editTextMonth.hint = (getString(R.string.month_of_birth))
                editTextMonth.isEnabled = false

                val zeroNumberText = getString(R.string.zero_number)

                numberOfYear.text = zeroNumberText
                numberOfMonths.text = zeroNumberText
                numberOfWeeks.text = zeroNumberText
                numberOfDays.text = zeroNumberText
                numberOfHours.text = zeroNumberText
                numberOfMinutes.text = zeroNumberText
            }
        }
    }

    private fun hideKeyboard() {
        binding.root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, ZERO)
        }
    }

    companion object {
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWELVE = 12
        private const val MAX_AGE = 120
        private const val EIGHTEEN = 18
        private const val EMPTY = ""
        private val PATTERN_DIGITS = ("\\d+").toRegex()
    }
}
