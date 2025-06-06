package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.data.repo.model.ArticleId

internal interface HomeNavigation {
    fun HomeScreen.navigateToArticleDetail(id: ArticleId)
}
