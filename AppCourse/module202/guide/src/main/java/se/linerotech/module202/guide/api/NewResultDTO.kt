package se.linerotech.module202.guide.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewResultDTO(
    @Json(name = "articles")
    val articles: List<ArticleDTO>? = listOf(),
    @Json(name = "status")
    val status: String? = "",
    @Json(name = "totalResults")
    val totalResults: Int? = 0
)
