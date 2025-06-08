package dev.matsem.spacenews.shared.feature.articleDetail.ui

import androidx.compose.ui.window.ComposeUIViewController
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailScreen

fun ArticleDetailScreenUiController(screen: ArticleDetailScreen) = ComposeUIViewController {
    SpaceNewsTheme {
        ArticleDetailScreenUi(screen)
    }
}
