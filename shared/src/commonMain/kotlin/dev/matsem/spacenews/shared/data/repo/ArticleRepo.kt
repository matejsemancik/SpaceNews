package dev.matsem.spacenews.shared.data.repo

import dev.matsem.spacenews.shared.data.mapping.ArticleMapping.toDomainModel
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.service.NewsApiManager

internal interface ArticleRepo {
    suspend fun getArticles(): List<Article>
}

internal class ArticleRepoImpl(private val apiManager: NewsApiManager) : ArticleRepo {

    override suspend fun getArticles(): List<Article> =
        apiManager
            .getArticles(10)
            .results
            .map { it.toDomainModel() }
}
