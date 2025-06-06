package dev.matsem.spacenews.shared.injection

import dev.matsem.spacenews.shared.data.repo.ArticleRepo
import dev.matsem.spacenews.shared.data.repo.ArticleRepoImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun repositoryModule() = module {
    singleOf(::ArticleRepoImpl) bind ArticleRepo::class
}
