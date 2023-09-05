package se.linerotech.module202.guide.newsDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import se.linerotech.module202.guide.R
import se.linerotech.module202.guide.common.News
import se.linerotech.module202.guide.databinding.ActivityNewsDetailsBinding

class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar()
        showDetails(news())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configureToolBar() {
        setSupportActionBar(binding.tollBar)
        supportActionBar?.apply {
            this.title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back)
        }
    }

    private fun news(): News {
        return intent.getParcelableExtra(KEY_NEWS)!!
    }

    private fun showDetails(news: News) {
        with(binding) {
            source.text = news.source
            date.text = news.date
            description.text = news.description
            binding.readStoryButton.setOnClickListener { openBrowser(url = news.url) }
            loadImage(news.image)
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun loadImage(url: String) {
        if (url.isNotEmpty()) {
            binding.image.setImageResource(R.drawable.general_image)
        }
        Glide
            .with(binding.image)
            .load(url)
            .placeholder(R.drawable.general_image)
            .centerCrop()
            .into(binding.image)
    }

    companion object {
        private const val KEY_NEWS = "KEY_NEWS"
        fun intent(context: Context, news: News): Intent {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra(KEY_NEWS, news)
            return intent
        }
    }
}
