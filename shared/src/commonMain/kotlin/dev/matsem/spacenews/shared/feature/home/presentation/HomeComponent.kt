package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.arch.AppComponentContext
import dev.matsem.spacenews.shared.arch.BaseComponent
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.InjectedParam

internal class HomeComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: HomeNavigation,
) :
    BaseComponent<HomeState, Nothing>(componentContext, HomeState),
    HomeScreen,
    HomeNavigation by navigation {

    override val state: StateFlow<HomeState>
        get() = componentState

    override val actions: HomeScreen.Actions = object : HomeScreen.Actions {

        override fun onArticleClick(id: ArticleId) = navigateToArticleDetail(id)
    }
}
