package dev.matsem.spacenews.shared.data.service

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.Url
import dev.matsem.spacenews.shared.data.service.model.ArticleResponse
import dev.matsem.spacenews.shared.data.service.model.PagedListResponse

internal interface NewsApi {

    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PagedListResponse<ArticleResponse>

    @GET("")
    suspend fun getArticles(
        @Url url: String,
    ): PagedListResponse<ArticleResponse>
}
