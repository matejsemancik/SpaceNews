package dev.matsem.spacenews.shared.injection

import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailComponent
import dev.matsem.spacenews.shared.feature.home.presentation.HomeComponent
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal fun componentModule() = module {
    factoryOf(::HomeComponent)
    factoryOf(::ArticleDetailComponent)
}
