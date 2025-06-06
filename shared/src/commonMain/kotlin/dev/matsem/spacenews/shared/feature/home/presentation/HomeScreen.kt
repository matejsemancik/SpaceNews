package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import kotlinx.coroutines.flow.StateFlow

interface HomeScreen {

    val state: StateFlow<HomeState>
    val actions: Actions

    interface Actions {
        fun onArticleClick(id: ArticleId)

        companion object {
            fun noOp() = object : Actions {
                override fun onArticleClick(id: ArticleId) = Unit
            }
        }
    }
}

