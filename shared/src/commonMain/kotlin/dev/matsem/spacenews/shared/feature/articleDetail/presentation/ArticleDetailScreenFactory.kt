package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import dev.matsem.spacenews.shared.arch.presentation.AppComponentContext
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal object ArticleDetailScreenFactory : KoinComponent {

    fun create(
        componentContext: AppComponentContext,
        navigation: ArticleDetailNavigation,
        articleId: ArticleId,
    ): ArticleDetailComponent {
        return get(parameters = { parametersOf(componentContext, navigation, articleId) })
    }
}
