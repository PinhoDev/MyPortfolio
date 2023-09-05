package se.linerotech.module202.guide.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import se.linerotech.module202.guide.api.ArticleDTO
import se.linerotech.module202.guide.api.Credentials
import se.linerotech.module202.guide.api.NewResultDTO
import se.linerotech.module202.guide.api.RetrofitClient
import se.linerotech.module202.guide.common.News

class NewsListViewModel(private val categoryName: String) : ViewModel() {
    private val _state = MutableStateFlow<NewsListState>(NewsListState.Loading)
    val state: StateFlow<NewsListState> = _state

    init {
        retrieveNews()
    }

    private fun retrieveNews() {
        headLines().enqueue(object : Callback<NewResultDTO> {
            override fun onResponse(call: Call<NewResultDTO>, response: Response<NewResultDTO>) {
                if (isSuccessful(response)) {
                    val news = news(response.body()!!.articles!!)
                    _state.value =
                        NewsListState.Success(
                            sources = sources(news),
                            news = news,
                        )
                } else {
                    _state.value = NewsListState.Failure
                }
            }

            override fun onFailure(call: Call<NewResultDTO>, t: Throwable) {
                _state.value = NewsListState.Failure
            }
        })
    }

    private fun isSuccessful(response: Response<NewResultDTO>): Boolean {
        return response.isSuccessful && response.body() != null && response.body()!!.articles != null
    }

    private fun headLines(): Call<NewResultDTO> {
        return RetrofitClient.instance.topHeadLines(
            apiKey = Credentials.apiKey,
            language = "en",
            country = "us",
            category = categoryName,
        )
    }

    private fun news(articles: List<ArticleDTO>): List<News> {
        return articles.map { dto -> News(dto) }
    }

    private fun sources(news: List<News>): List<String> {
        return news.map { it.source }.filter { it.isNotEmpty() }.distinct().sortedBy { it.first() }
    }

    companion object {
        fun factory(categoryName: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewsListViewModel(categoryName) as T
                }
            }
        }
    }
}
