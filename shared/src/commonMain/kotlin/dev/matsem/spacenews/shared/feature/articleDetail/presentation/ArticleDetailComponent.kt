package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import dev.matsem.spacenews.shared.arch.AppComponentContext
import dev.matsem.spacenews.shared.arch.BaseComponent
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.InjectedParam

internal class ArticleDetailComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: ArticleDetailNavigation,
) :
    BaseComponent<ArticleDetailState, Nothing>(componentContext, ArticleDetailState),
    ArticleDetailScreen,
    ArticleDetailNavigation by navigation {

    override val state: StateFlow<ArticleDetailState>
        get() = componentState

    override val actions: ArticleDetailScreen.Actions = object : ArticleDetailScreen.Actions {
        override fun onBack() = navigateBack()
    }
}
