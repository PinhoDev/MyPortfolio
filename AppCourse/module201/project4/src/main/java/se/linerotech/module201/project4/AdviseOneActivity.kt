package se.linerotech.module201.project4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project4.databinding.ActivityAdviseOneBinding

class AdviseOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdviseOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdviseOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickNext()
    }

    private fun setClickNext() {
        binding.textNextOne.setOnClickListener {
            startActivity(Intent(this, AdviseTwoActivity::class.java))
        }
    }
}
