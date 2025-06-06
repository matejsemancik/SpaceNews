package dev.matsem.spacenews.shared.data.service

import dev.matsem.spacenews.shared.data.service.model.ArticleResponse
import dev.matsem.spacenews.shared.data.service.model.PagedListResponse

internal interface NewsApiManager {

    suspend fun getArticles(limit: Int): PagedListResponse<ArticleResponse>
    suspend fun getArticles(nextUrl: String): PagedListResponse<ArticleResponse>
}

internal class NewsApiManagerImpl(private val api: NewsApi) : NewsApiManager {

    override suspend fun getArticles(limit: Int): PagedListResponse<ArticleResponse> =
        api.getArticles(limit = limit, offset = 0)

    override suspend fun getArticles(nextUrl: String): PagedListResponse<ArticleResponse> =
        api.getArticles(url = nextUrl)
}
