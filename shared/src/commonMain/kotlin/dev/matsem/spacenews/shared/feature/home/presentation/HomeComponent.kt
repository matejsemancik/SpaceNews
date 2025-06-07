package dev.matsem.spacenews.shared.feature.home.presentation

import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.matsem.spacenews.shared.arch.presentation.AppComponentContext
import dev.matsem.spacenews.shared.arch.presentation.BaseComponent
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.domain.FetchArticlesCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.InjectedParam

internal class HomeComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: HomeNavigation,
    private val fetchArticlesCase: FetchArticlesCase,
) :
    BaseComponent<HomeState, Nothing>(componentContext, HomeState()),
    HomeScreen,
    HomeNavigation by navigation {

    override val state: StateFlow<HomeState>
        get() = componentState

    override val actions: HomeScreen.Actions = object : HomeScreen.Actions {

        override fun onArticleClick(id: ArticleId) = navigateToArticleDetail(id)
        override fun onFetchMore() = fetchArticles()
    }

    init {
        doOnCreate {
            fetchArticles()
        }
    }

    private fun fetchArticles() {
        fetchArticlesCase.execute(
            input = componentState.value.articlePagedListState,
            onNext = { pagedList ->
                componentState.update { state -> state.copy(articlePagedListState = pagedList) }
            },
        )
    }
}
