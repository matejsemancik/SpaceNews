package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticlePagedListState
import dev.matsem.spacenews.shared.data.repo.model.initialPagedListState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    internal val articlePagedListState: ArticlePagedListState = initialPagedListState<Article>(initialPageSize = 20),
) {
    val articles: ImmutableList<Article>
        get() = articlePagedListState.data.toImmutableList()
}
