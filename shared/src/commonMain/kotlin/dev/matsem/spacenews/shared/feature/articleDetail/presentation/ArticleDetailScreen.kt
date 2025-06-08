package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import kotlinx.coroutines.flow.StateFlow

interface ArticleDetailScreen {

    val state: StateFlow<ArticleDetailState>
    val actions: Actions

    interface Actions {
        fun onBack()
        fun onRetryClick()

        companion object {
            fun noOp() = object : Actions {
                override fun onBack() = Unit
                override fun onRetryClick() = Unit
            }
        }
    }
}

