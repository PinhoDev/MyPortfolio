package se.linerotech.module202.guide.selectCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import se.linerotech.module202.guide.R
import se.linerotech.module202.guide.common.Category

class CategoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(categories())
    val state: StateFlow<List<Category>> = _state

    private fun categories(): List<Category> {
        return listOf(
            Category(id = "general", R.string.general, R.drawable.general_image),
            Category(id = "business", R.string.business, R.drawable.business_image),
            Category(id = "entertainment", R.string.entertainment, R.drawable.entertainment_image),
            Category(id = "health", R.string.health, R.drawable.health_image),
            Category(id = "sports", R.string.sports, R.drawable.sports_image),
            Category(id = "science", R.string.science, R.drawable.science_image),
            Category(id = "technology", R.string.technology, R.drawable.technology_image)
        )
    }

    companion object {
        fun factory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CategoryViewModel() as T
                }
            }
        }
    }
}
