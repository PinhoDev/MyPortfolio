package se.linerotech.module201.project1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar()
        setClickLogin()
        setClickRegister()
    }

    private fun configureToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setClickLogin() {
        binding.buttonLogin.setOnClickListener {
            validateCredentials()
        }
    }

    private fun validateCredentials() {
        if (!isEmailValid()) {
            notifyInvalidEmail()
        }
        if (!isPasswordValid()) {
            notifyInvalidPassword()
        }
        if (isEmailValid() && isPasswordValid()) {
            sendMessage(message = R.string.successful_login)
        }
    }

    private fun isEmailValid(): Boolean {
        val email = binding.editTextEmail.text.toString().trim()
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(): Boolean {
        val password = binding.editTextPassword.text.toString().trim()
        return password.isNotEmpty()
    }

    private fun notifyInvalidEmail() {
        binding.editTextEmail.error = getString(R.string.invalid_email)
    }

    private fun notifyInvalidPassword() {
        binding.editTextPassword.error = getString(R.string.invalid_password)
    }

    private fun sendMessage(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setClickRegister() {
        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
