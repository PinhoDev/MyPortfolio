package se.linerotech.module201.guide

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.guide.databinding.ActivityCreateAccountBinding

class CreateAccount : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar()
        setClickListener()
    }

    private fun configureToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setClickListener() {
        binding.buttonCreateAccount.setOnClickListener {
            validateNewAccount()
        }
    }

    private fun validateNewAccount() {
        if (!isValidUsername()) {
            notifyInvalidUsername()
        }
        if (!isNewEmailValid()) {
            notifyInvalidNewEmail()
        }
        if (!isNewPasswordValid()) {
            notifyInvalidNewPassword()
        }
        if (isValidUsername() && isNewEmailValid() && isNewPasswordValid()) {
            Toast.makeText(this, R.string.account_created, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidUsername(): Boolean {
        val username = binding.editTextUsername.text.toString().trim()
        return username.isNotEmpty()
    }

    private fun isNewEmailValid(): Boolean {
        val email = binding.editTextNewEmail.text.toString().trim()
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isNewPasswordValid(): Boolean {
        val password = binding.editTextNewPassword.text.toString().trim()
        return password.isNotEmpty()
    }

    private fun notifyInvalidUsername() {
        binding.editTextUsername.error = getString(R.string.username_empty)
    }

    private fun notifyInvalidNewEmail() {
        binding.editTextNewEmail.error = getString(R.string.invalid_email)
    }

    private fun notifyInvalidNewPassword() {
        binding.editTextNewPassword.error = getString(R.string.invalid_password)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
