package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.ArticlePagedListState
import dev.matsem.spacenews.shared.data.repo.model.initialPagedListState
import dev.matsem.spacenews.shared.feature.home.model.ArticleListItem
import dev.matsem.spacenews.shared.feature.home.model.LoadingFooterItem
import dev.matsem.spacenews.shared.feature.home.model.toUiListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeState(
    internal val articlePagedListState: ArticlePagedListState = initialPagedListState<Article>(pageSize = 20),
) {
    val articles: ImmutableList<ArticleListItem>
        get() = articlePagedListState.data.map { it.toUiListItem() }.toImmutableList()

    val loadingFooter: LoadingFooterItem?
        get() = when {
            articlePagedListState.error != null -> LoadingFooterItem.Error(articlePagedListState.error)
            articlePagedListState.isLoading -> LoadingFooterItem.Loading
            else -> null
        }
}
