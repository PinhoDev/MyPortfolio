package se.linerotech.module201.project2

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project2.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val MAX_PERCENTAGE = 20
        private const val DIV_PERCENTAGE = 100
        private const val ZERO = 0
    }

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickOutside()
        setupButtonListeners()
        setupNewAmount()
    }

    private fun validAmount(): Boolean {
        val value = binding.editTextAmount.text.toString().trim()
        val pattern = ("^(?!.*\\.\\.).*$").toRegex()

        return if (!value.matches(pattern)) {
            binding.editTextAmount.error = getString(R.string.invalid_amount)
            false
        } else {
            true
        }
    }

    private fun getAmount(): String {
        val value = binding.editTextAmount.text.toString().trim()

        return if (value.isNotEmpty() && validAmount()) {
            value
        } else {
            ZERO.toString()
        }
    }

    private fun setupClickOutside() {
        binding.root.setOnClickListener {
            hideKeyboard()
            showBill()
            showTotalAmountAndTip()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun showBill() {
        val userAmount = getAmount()
        if (userAmount.isNotEmpty()) {
            binding.totalBill.text = formatCurrency(userAmount)
        }
    }

    private fun setupButtonListeners() {
        binding.buttonIncrease.setOnClickListener {
            hideKeyboard()
            showBill()
            updateCounter(true)
            showCounter()
            showTotalAmountAndTip()
        }

        binding.buttonDecrease.setOnClickListener {
            hideKeyboard()
            showBill()
            updateCounter(false)
            showCounter()
            showTotalAmountAndTip()
        }
    }

    private fun updateCounter(increase: Boolean) {
        if (increase && counter < MAX_PERCENTAGE) {
            counter++
        } else if (!increase && counter > ZERO) {
            counter--
        }
    }

    private fun showCounter() {
        binding.textViewPercentage.text = getString(R.string.percentage_symbol, counter)
    }

    private fun totalTip(): Double {
        val userAmount = getAmount().toDouble()
        val tips = counter.toDouble()
        return userAmount * (tips / DIV_PERCENTAGE)
    }

    private fun totalAmount(): Double {
        val userAmount = getAmount().toDouble()
        val totalTips = totalTip()
        return userAmount + totalTips
    }

    private fun showTotalAmountAndTip() {
        val totalAmount = totalAmount()
        val totalTips = totalTip().toString()

        binding.totalTip.text = formatCurrency(totalTips)
        binding.totalAmount.text = formatCurrency(totalAmount.toString())
    }

    private fun setupNewAmount() {
        binding.textViewRefresh.setOnClickListener {
            binding.editTextAmount.setText("")
            binding.textViewPercentage.text = getString(R.string.percentage_symbol, ZERO)
            binding.totalAmount.text = getString(R.string.empty_value)
            binding.totalBill.text = getString(R.string.empty_value)
            binding.totalTip.text = getString(R.string.empty_value)
        }
    }

    private fun formatCurrency(value: String): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(
            Locale(
                getString(R.string.language),
                getString(R.string.country)
            )
        )
        return currencyFormat.format(value.toDouble()).toString()
    }
}
