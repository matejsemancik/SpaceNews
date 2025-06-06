package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import dev.matsem.spacenews.shared.arch.AppComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal object ArticleDetailScreenFactory : KoinComponent {

    fun create(componentContext: AppComponentContext, navigation: ArticleDetailNavigation): ArticleDetailComponent {
        return get(parameters = { parametersOf(componentContext, navigation) })
    }
}
