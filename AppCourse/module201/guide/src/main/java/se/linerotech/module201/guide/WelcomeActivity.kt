package se.linerotech.module201.guide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    private var startLogin: Button? = null
    private var startCreateAccount: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        initializeViews()
        setClickListener()
    }

    private fun initializeViews() {
        startLogin = findViewById(R.id.ButtonLogin)
        startCreateAccount = findViewById(R.id.ButtonCreateAccount)
    }

    private fun setClickListener() {
        startCreateAccount?.setOnClickListener {
            startActivity(Intent(this, CreateAccount::class.java))
        }

        startLogin?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
