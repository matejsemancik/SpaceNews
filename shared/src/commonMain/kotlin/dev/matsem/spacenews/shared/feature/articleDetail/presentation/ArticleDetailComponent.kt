package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import com.arkivanov.essenty.lifecycle.doOnCreate
import dev.matsem.spacenews.shared.arch.presentation.AppComponentContext
import dev.matsem.spacenews.shared.arch.presentation.BaseComponent
import dev.matsem.spacenews.shared.arch.state.LoadingState
import dev.matsem.spacenews.shared.arch.state.toUiError
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.domain.GetArticleCase
import dev.matsem.spacenews.shared.feature.articleDetail.model.toUiDetail
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.InjectedParam

internal class ArticleDetailComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val navigation: ArticleDetailNavigation,
    @InjectedParam private val articleId: ArticleId,
    private val getArticleCase: GetArticleCase,
) :
    BaseComponent<ArticleDetailState, Nothing>(componentContext, ArticleDetailState()),
    ArticleDetailScreen,
    ArticleDetailNavigation by navigation {

    override val state: StateFlow<ArticleDetailState>
        get() = componentState

    override val actions: ArticleDetailScreen.Actions = object : ArticleDetailScreen.Actions {
        override fun onBack() = navigateBack()
        override fun onRetryClick() = getArticle()
    }

    init {
        doOnCreate {
            getArticle()
        }
    }

    private fun getArticle() {
        getArticleCase.execute(
            input = articleId,
            onStart = {
                componentState.update { it.copy(article = LoadingState.Loading) }
            },
            onNext = { article ->
                componentState.update { it.copy(article = LoadingState.Data(article.toUiDetail())) }
            },
            onError = { error ->
                componentState.update { it.copy(article = LoadingState.Error(error.toUiError())) }
            },
        )
    }
}
