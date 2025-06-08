package dev.matsem.spacenews.shared.domain

import dev.matsem.spacenews.shared.arch.useCase.FlowUseCase
import dev.matsem.spacenews.shared.data.repo.ArticleRepo
import dev.matsem.spacenews.shared.data.repo.model.ArticlePagedListState
import kotlinx.coroutines.flow.Flow

internal class FetchPagedArticlesCase(
    private val articleRepo: ArticleRepo,
) : FlowUseCase<ArticlePagedListState, ArticlePagedListState>() {

    override fun invoke(input: ArticlePagedListState): Flow<ArticlePagedListState> = articleRepo.fetchArticles(input)
}
