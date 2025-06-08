package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.util.DebugLogger
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.resources.compose.stringResource
import dev.matsem.spacenews.resources.MR
import dev.matsem.spacenews.shared.data.repo.model.Article
import dev.matsem.spacenews.shared.data.repo.model.mockPagedListState
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

    // This is first common Composable entrypoint displayed in both apps,
    // thus we initialise ImageLoader factory here
    val coilPlatformContext = LocalPlatformContext.current
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .logger(DebugLogger())
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(coilPlatformContext, percent = 0.2)
                    .build()
            }
            .build()
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun Content(
    state: HomeState,
    actions: HomeScreen.Actions,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val hazeState = rememberHazeState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }.collect { layoutInfo ->
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.takeIf { it.isNotEmpty() }?.maxOf { it.index } ?: return@collect
            if (lastVisibleItemIndex >= totalItemsCount - 1) {
                actions.onFetchMore()
            }
        }
    }

    Scaffold(
        topBar = {
            HomeAppBar(text = stringResource(MR.strings.home_title), hazeState = hazeState)
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .background(SpaceNewsTheme.colorScheme.background)
                .hazeSource(hazeState),
            state = listState,
            contentPadding = paddingValues,
        ) {
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
