package dev.matsem.spacenews.shared.feature.articleDetail.presentation

import kotlinx.coroutines.flow.StateFlow

interface ArticleDetailScreen {

    val state: StateFlow<ArticleDetailState>
    val actions: Actions

    interface Actions {
        fun onBack()

        companion object {
            fun noOp() = object : Actions {
                override fun onBack() = Unit
            }
        }
    }
}

