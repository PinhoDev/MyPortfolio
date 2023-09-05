package se.linerotech.module201.project4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project4.databinding.ActivityAdviseThreeBinding

class AdviseThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdviseThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdviseThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickBegin()
    }

    private fun setClickBegin() {
        binding.textBegin.setOnClickListener {
            startActivity(Intent(this, MortgageActivity::class.java))
        }
    }
}
