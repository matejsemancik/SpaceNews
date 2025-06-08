package dev.matsem.spacenews.shared.data.repo

import dev.matsem.spacenews.shared.data.mapping.ArticleMapping.toDomainModel
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.data.repo.model.NextPagePointer
import dev.matsem.spacenews.shared.data.repo.model.PagedListState
import dev.matsem.spacenews.shared.data.repo.model.error
import dev.matsem.spacenews.shared.data.repo.model.loading
import dev.matsem.spacenews.shared.data.service.NewsApiManager
import dev.matsem.spacenews.shared.data.service.model.ArticleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

/**
 * Repository interface for managing article data.
 * Provides methods to fetch articles in a paginated manner and retrieve individual articles.
 */
internal interface ArticleRepo {
    /**
     * Fetches a paginated list of articles.
     * @param list Current state of the paginated list
     * @return Flow emitting updated paginated list states
     */
    fun fetchArticles(list: PagedListState<Article>): Flow<PagedListState<Article>>

    /**
     * Retrieves a single article by its ID.
     * @param id Unique identifier of the article
     * @return Flow emitting the requested article
     */
    fun getArticle(id: ArticleId): Flow<Article>
}

internal class ArticleRepoImpl(private val apiManager: NewsApiManager) : ArticleRepo {

    // In-memory cache is used to quickly query Article by its ID when user navigates to detail
    private val inMemoryCache = mutableMapOf<ArticleId, ArticleResponse>()

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

        inMemoryCache.putAll(articlesResponse.results.map { ArticleId(it.id) to it })

        val newList = list.copy(
            nextPage = articlesResponse.next?.let { NextPagePointer.Url(nextPageUrl = it) },
            data = list.data + articlesResponse.results.map { it.toDomainModel() },
            error = null,
            isLoading = false,
        )

        emit(newList)
    }

    override fun getArticle(id: ArticleId): Flow<Article> = flow {
        // First emit cached article
        val cachedArticle = inMemoryCache[id]
        if (cachedArticle != null) {
            emit(cachedArticle.toDomainModel())
        }

        // Then fetch the latest version from API
        val apiArticle = runCatching { apiManager.getArticle(id.id) }
        apiArticle.fold(
            onSuccess = { emit(it.toDomainModel()) },
            onFailure = { error ->
                // Throw error if there was no cached article emission before, otherwise fail gracefully
                if (cachedArticle == null) {
                    throw error
                }
            },
        )
    }.distinctUntilChanged()
}
