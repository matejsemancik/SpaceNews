package dev.matsem.spacenews.shared.data.repo

import dev.matsem.spacenews.shared.data.mapping.ArticleMapping.toDomainModel
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.NextPagePointer
import dev.matsem.spacenews.shared.data.repo.model.PagedListState
import dev.matsem.spacenews.shared.data.repo.model.error
import dev.matsem.spacenews.shared.data.repo.model.loading
import dev.matsem.spacenews.shared.data.service.NewsApiManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal interface ArticleRepo {
    suspend fun getArticles(): List<Article>

    fun fetchArticles(list: PagedListState<Article>): Flow<PagedListState<Article>>
}

internal class ArticleRepoImpl(private val apiManager: NewsApiManager) : ArticleRepo {

    override suspend fun getArticles(): List<Article> =
        apiManager
            .getArticles(10, 0)
            .results
            .map { it.toDomainModel() }

    override fun fetchArticles(list: PagedListState<Article>): Flow<PagedListState<Article>> = flow {
        val nextPage = list.nextPage ?: run {
            emit(list)
            return@flow
        }

        emit(list.loading())

        val articlesResponse = runCatching {
            when (nextPage) {
                is NextPagePointer.LimitOffset -> apiManager.getArticles(limit = nextPage.limit, offset = nextPage.offset)
                is NextPagePointer.Url -> apiManager.getArticles(nextUrl = nextPage.nextPageUrl)
            }
        }.getOrElse { throwable ->
            emit(list.error(throwable))
            return@flow
        }

        val newList = list.copy(
            nextPage = articlesResponse.next?.let { NextPagePointer.Url(nextPageUrl = it) },
            hasReachedEnd = articlesResponse.next == null,
            data = list.data + articlesResponse.results.map { it.toDomainModel() },
            error = null,
            isLoading = false,
        )

        emit(newList)
    }
}
