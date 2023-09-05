package se.linerotech.module201.project1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar()
        setClickRegister()
        setClickLogin()
    }

    private fun configureToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setClickRegister() {
        binding.buttonRegister.setOnClickListener {
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
            Toast.makeText(this, getString(R.string.account_created), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidUsername(): Boolean {
        val username = binding.editTextUsername.text.toString().trim()
        return username.isNotEmpty()
    }

    private fun isNewEmailValid(): Boolean {
        val email = binding.editTextEmail.text.toString().trim()
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isNewPasswordValid(): Boolean {
        val password = binding.editTextPassword.text.toString().trim()
        return password.isNotEmpty()
    }

    private fun notifyInvalidUsername() {
        binding.editTextUsername.error = getString(R.string.username_empty)
    }

    private fun notifyInvalidNewEmail() {
        binding.editTextEmail.error = getString(R.string.invalid_email)
    }

    private fun notifyInvalidNewPassword() {
        binding.editTextPassword.error = getString(R.string.invalid_password)
    }

    private fun setClickLogin() {
        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
