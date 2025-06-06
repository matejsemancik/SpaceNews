package dev.matsem.spacenews.shared.data.repo.model

import kotlinx.datetime.Instant

data class Article(
    val id: ArticleId,
    val title: String,
    val authors: List<String>,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: Instant,
)
