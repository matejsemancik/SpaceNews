package dev.matsem.spacenews.shared.data.mapping

import dev.matsem.spacenews.shared.data.database.entity.ArticleEntity
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.data.service.model.ArticleResponse

internal object ArticleMapping {

    fun ArticleResponse.toDomainModel() = Article(
        id = ArticleId(id = id),
        title = title,
        authors = authors.map { it.name },
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
    )

    fun ArticleEntity.toDomainModel() = Article(
        id = ArticleId(id = id),
        title = title,
        authors = authors,
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
    )

    fun ArticleResponse.toDbModel() = ArticleEntity(
        id = id,
        title = title,
        authors = authors.map { it.name },
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
    )

    fun Article.toDbModel() = ArticleEntity(
        id = id.id,
        title = title,
        authors = authors,
        url = url,
        imageUrl = imageUrl,
        summary = summary,
        publishedAt = publishedAt,
    )
}
