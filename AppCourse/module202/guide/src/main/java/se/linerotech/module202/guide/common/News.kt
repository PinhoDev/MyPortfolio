package se.linerotech.module202.guide.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import se.linerotech.module202.guide.api.ArticleDTO

@Parcelize
data class News(
    val title: String,
    val date: String,
    val source: String,
    val image: String,
    val description: String,
    val url: String
) : Parcelable {
    constructor(dto: ArticleDTO) : this(
        title = dto.title ?: "",
        date = dto.publishedAt?.split("T")?.first() ?: "",
        source = dto.source!!.name ?: "",
        image = dto.urlToImage ?: "",
        description = dto.description ?: "",
        url = dto.url ?: "",
    )
}
