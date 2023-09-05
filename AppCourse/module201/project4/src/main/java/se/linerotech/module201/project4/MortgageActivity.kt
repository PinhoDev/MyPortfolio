package se.linerotech.module201.project4

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.method.DigitsKeyListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project4.databinding.ActivityMortgageBinding
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.pow

class MortgageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMortgageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMortgageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextActionAmount()
        nextActionRate()
        actionDoneYears()
        setupButtonApply()
        hideKeyboard()
        configureToolBar()
        decimalSeparatorKeyboard()
    }

    private fun nextActionAmount() {
        binding.editTextAmount.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val amount = binding.editTextAmount.text.toString().trim()
                if (isValidAmount(amount)) {
                    val sign = signValues(amount, getString(R.string.amount_sign))
                    with(binding) {
                        editTextAmount.setText(sign)
                        editTextRate.isEnabled = true
                        editTextRate.requestFocus()
                        editTextAmount.isEnabled = false
                    }
                }
            }
            false
        }
    }

    private fun isValidAmount(amount: String): Boolean {
        return when {
            amount.isEmpty() -> {
                binding.editTextAmount.error = (getString(R.string.enter_amount))
                false
            }

            amount.matches(DOUBLE_DECIMAL) -> {
                binding.editTextAmount.error = (getString(R.string.invalid_amount))
                false
            }

            else -> true
        }
    }

    private fun nextActionRate() {
        binding.editTextRate.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                val rate = binding.editTextRate.text.toString().trim()
                if (isValidRate(rate)) {
                    val sign = signValues(rate, getString(R.string.rate_sign))
                    with(binding) {
                        editTextRate.setText(sign)
                        editTextYear.isEnabled = true
                        editTextYear.requestFocus()
                        editTextRate.isEnabled = false
                    }
                }
            }
            false
        }
    }

    private fun isValidRate(rate: String): Boolean {
        return when {
            rate.isEmpty() -> {
                binding.editTextRate.error = (getString(R.string.enter_rate))
                false
            }

            rate.matches(DOUBLE_DECIMAL) -> {
                binding.editTextRate.error = (getString(R.string.invalid_amount))
                false
            }

            else -> {
                true
            }
        }
    }

    private fun actionDoneYears() {
        binding.editTextYear.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val years = binding.editTextYear.text.toString().trim()
                if (isValidYears(years)) {
                    with(binding) {
                        editTextYear.filters = arrayOf(InputFilter.LengthFilter(LENGTH))
                        editTextYear.setText(getString(R.string.years_sign, years))
                        buttonApply.isEnabled = true
                        editTextYear.isEnabled = false
                    }
                }
                true
            } else {
                false
            }
        }
    }

    private fun isValidYears(years: String): Boolean {
        return when {
            years.isEmpty() -> {
                binding.editTextYear.error = (getString(R.string.enter_year))
                false
            }

            years.matches(DOUBLE_ZERO) -> {
                binding.editTextYear.error = (getString(R.string.invalid_year))
                false
            }

            else -> true
        }
    }

    private fun signValues(value: String, sign: String): String {
        val decimalFormat =
            Locale(getString(R.string.language_format), getString(R.string.country_format))

        return String.format(decimalFormat, sign, value.toDouble())
    }

    private fun setupButtonApply() {
        binding.buttonApply.setOnClickListener {
            val amount = getUserInputValue(binding.editTextAmount)
            val rate = getUserInputValue(binding.editTextRate)
            val years = getUserInputValue(binding.editTextYear)

            val loanAmount = VALID_VALUE.find(amount)?.value?.toDouble() ?: ZERO.toDouble()
            val loanRate = VALID_VALUE.find(rate)?.value?.toDouble() ?: ZERO.toDouble()
            val loanYears = VALID_VALUE.find(years)?.value?.toInt() ?: ZERO

            val mortgage = calculateMortgage(loanAmount, loanRate, loanYears)
            showMortgage(mortgage)
        }
    }

    private fun getUserInputValue(editText: EditText): String {
        val userInput = editText.text.toString().trim()
        return parseUserInput(userInput)
    }

    private fun parseUserInput(input: String): String {
        val inputWithoutSpaces = input.filter { it.isDigit() || it == COMMA || it == DOT }
        return inputWithoutSpaces.replace(COMMA, DOT)
    }

    private fun calculateMortgage(amount: Double, rate: Double, years: Int): Int {
        val monthlyInterest = rate / PERCENT / MONTHS
        val loanTermInMonths = (years * MONTHS).toDouble()
        val numerator =
            amount * (monthlyInterest * (ONE + monthlyInterest).pow(loanTermInMonths))
        val denominator = (ONE + monthlyInterest).pow(loanTermInMonths) - ONE

        return (numerator / denominator).toInt()
    }

    private fun showMortgage(mortgage: Int) {
        binding.textViewMortgage.text = getString(R.string.mortgage_sign, mortgage)
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
            editTextAmount.keyListener =
                DigitsKeyListener.getInstance(DIGITS + symbols.decimalSeparator)
            editTextRate.keyListener =
                DigitsKeyListener.getInstance(DIGITS + symbols.decimalSeparator)
        }
    }

    private fun configureToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private const val ZERO = 0
        private const val MONTHS = 12
        private const val ONE = 1
        private const val PERCENT = 100
        private const val COMMA = ','
        private const val DOT = '.'
        private const val LENGTH = 8
        private const val DIGITS = "1234567890"
        private val VALID_VALUE = ("\\d+(?:[.]\\d+)?").toRegex()
        private val DOUBLE_DECIMAL = (".*[,.].*[,.].*").toRegex()
        private val DOUBLE_ZERO = ("^0{2}.*$").toRegex()
    }
}
