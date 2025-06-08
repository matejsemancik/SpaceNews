package dev.matsem.spacenews.shared.data.service.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(

    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("authors")
    val authors: List<AuthorResponse>,

    @SerialName("url")
    val url: String,

    @SerialName("image_url")
    val imageUrl: String,

    @SerialName("summary")
    val summary: String,

    @SerialName("published_at")
    val publishedAt: Instant,

    @SerialName("news_site")
    val newsSiteName: String
)
