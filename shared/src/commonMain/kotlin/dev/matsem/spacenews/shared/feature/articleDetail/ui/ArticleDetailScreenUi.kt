package dev.matsem.spacenews.shared.feature.articleDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.matsem.spacenews.shared.design.layout.DefaultStateLayout
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.design.tooling.Showcase
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailScreen
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ArticleDetailScreenUi(
    articleDetailScreen: ArticleDetailScreen,
    modifier: Modifier = Modifier,
) {
    val state by articleDetailScreen.state.collectAsStateWithLifecycle()
    val actions = articleDetailScreen.actions

    Content(state, actions, modifier)
}

@Composable
private fun Content(
    state: ArticleDetailState,
    actions: ArticleDetailScreen.Actions,
    modifier: Modifier = Modifier,
) {
    DefaultStateLayout(
        modifier = modifier.background(SpaceNewsTheme.colorScheme.background),
        loadingState = state.article,
        onRetryClick = actions::onRetryClick,
    ) { article ->
        Text(state.article.toString(), Modifier.padding(SpaceNewsTheme.dimensions.horizontalContentPadding))
    }
}

@Composable
@Preview
fun ArticleDetailScreenPreview() {
    Showcase {
        Content(
            state = ArticleDetailState(),
            actions = ArticleDetailScreen.Actions.noOp(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
