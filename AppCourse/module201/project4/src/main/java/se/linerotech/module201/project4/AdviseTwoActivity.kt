package se.linerotech.module201.project4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.linerotech.module201.project4.databinding.ActivityAdviseTwoBinding

class AdviseTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdviseTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdviseTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickNext()
    }

    private fun setClickNext() {
        binding.textNextTwo.setOnClickListener {
            startActivity(Intent(this, AdviseThreeActivity::class.java))
        }
    }
}
