package se.linerotech.module202.guide.newsList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import se.linerotech.module202.guide.R
import se.linerotech.module202.guide.common.Category
import se.linerotech.module202.guide.common.News
import se.linerotech.module202.guide.databinding.ActivityNewsListBinding
import se.linerotech.module202.guide.databinding.LayoutChipBinding
import se.linerotech.module202.guide.newsDetails.NewsDetailsActivity

class NewsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsListBinding
    private val viewModel: NewsListViewModel by viewModels { NewsListViewModel.factory(category().id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolBar(title = category().name)
        observeState()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configureToolBar(@StringRes title: Int) {
        setSupportActionBar(binding.tollBar)
        supportActionBar?.apply {
            this.title = getString(title)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.arrow_back)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        NewsListState.Loading -> showProgressBar()
                        is NewsListState.Success -> {
                            showSources(state.sources)
                            showNews(state.news)
                        }

                        NewsListState.Failure -> showErrorMessage()
                    }
                }
            }
        }
    }

    private fun category(): Category {
        return intent.getParcelableExtra(KEY_CATEGORY)!!
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.unable_to_retrieve_news), Toast.LENGTH_SHORT).show()
    }

    private fun showSources(sources: List<String>) {
        sources.forEach { source ->
            val chip = createChip(source)
            binding.chipGroup.addView(chip)
        }
    }

    private fun showNews(news: List<News>) {
        hideProgressBar()

        binding.recyclerView.apply {
            visibility = View.VISIBLE
            addItemDecoration(DividerItemDecoration(this@NewsListActivity, LinearLayout.VERTICAL))
            adapter =
                NewsViewAdapter(items = news, onCellClicked = ::showNewsDetails)
            layoutManager = LinearLayoutManager(this@NewsListActivity)
            setHasFixedSize(true)
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun createChip(label: String): Chip {
        val chip = LayoutChipBinding.inflate(layoutInflater).root
        chip.text = label
        chip.setOnCloseIconClickListener {
            Toast.makeText(this, label, Toast.LENGTH_SHORT).show()
        }
        return chip
    }

    private fun showNewsDetails(news: News) {
        val intent = NewsDetailsActivity.intent(this, news)
        startActivity(intent)
    }

    companion object {
        private const val KEY_CATEGORY = "CATEGORY_NAME"
        fun intent(context: Context, category: Category): Intent {
            val intent = Intent(context, NewsListActivity::class.java)
            intent.putExtra(KEY_CATEGORY, category)
            return intent
        }
    }
}
