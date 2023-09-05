package se.linerotech.module202.guide.newsList

import se.linerotech.module202.guide.common.News

sealed class NewsListState {
    object Loading : NewsListState()
    data class Success(val news: List<News>, val sources: List<String>) : NewsListState()

    object Failure : NewsListState()
}
