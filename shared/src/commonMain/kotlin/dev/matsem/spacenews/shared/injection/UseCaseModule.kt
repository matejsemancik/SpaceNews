package dev.matsem.spacenews.shared.injection

import dev.matsem.spacenews.shared.domain.FetchArticlesCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal fun useCaseModule() = module {
    factoryOf(::FetchArticlesCase)
}
