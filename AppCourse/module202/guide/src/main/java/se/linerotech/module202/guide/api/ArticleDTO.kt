package se.linerotech.module202.guide.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDTO(
    @Json(name = "author")
    val author: String? = "",
    @Json(name = "content")
    val content: String? = "",
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "publishedAt")
    val publishedAt: String? = "",
    @Json(name = "source")
    val source: SourceDTO? = SourceDTO(),
    @Json(name = "title")
    val title: String? = "",
    @Json(name = "url")
    val url: String? = "",
    @Json(name = "urlToImage")
    val urlToImage: String? = ""
)
