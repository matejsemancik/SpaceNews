package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import dev.matsem.spacenews.shared.arch.state.LoadingState
import dev.matsem.spacenews.shared.feature.articleDetail.model.ArticleDetail

data class ArticleDetailState(
    val article: LoadingState<ArticleDetail> = LoadingState.Loading,
)
