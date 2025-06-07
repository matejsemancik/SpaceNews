package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.icerock.moko.resources.compose.stringResource
import dev.matsem.spacenews.resources.MR
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.mockPagedListState
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.design.tooling.Showcase
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreen
import dev.matsem.spacenews.shared.feature.home.presentation.HomeState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreenUi(
    homeScreen: HomeScreen,
    modifier: Modifier = Modifier,
) {
    val state by homeScreen.state.collectAsStateWithLifecycle()
    val actions = homeScreen.actions

    Content(state, actions, modifier)
}

@Composable
private fun Content(
    state: HomeState,
    actions: HomeScreen.Actions,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }.collect { layoutInfo ->
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.takeIf { it.isNotEmpty() }?.maxOf { it.index } ?: return@collect
            if (lastVisibleItemIndex >= totalItemsCount - 1) {
                actions.onFetchMore()
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        item {
            Spacer(Modifier.statusBarsPadding())
        }
        item {
            Column(Modifier.padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding, vertical = Grid.d2)) {
                Text(
                    text = stringResource(MR.strings.home_title),
                    style = SpaceNewsTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = SpaceNewsTheme.colorScheme.onBackground,
                )
            }
        }
        items(state.articles) { article ->
            ArticleListRow(article, Modifier.fillMaxWidth(), onClick = { actions.onArticleClick(id = article.id) })
        }
        item {
            AnimatedVisibility(visible = state.loadingFooter != null, enter = fadeIn(), exit = fadeOut()) {
                state.loadingFooter?.let { footerItem ->
                    LoadingFooterRow(footerItem, modifier = Modifier.fillMaxWidth(), onRetryClick = actions::onFooterRetryClick)
                }
            }
        }
        item {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    Showcase {
        Content(
            state = HomeState(articlePagedListState = mockPagedListState(Article.mocks())),
            actions = HomeScreen.Actions.noOp(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
