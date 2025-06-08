package dev.matsem.spacenews.shared.feature.articleDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.resources.compose.localized
import dev.matsem.spacenews.shared.arch.state.LoadingState
import dev.matsem.spacenews.shared.design.layout.DefaultStateLayout
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.design.tooling.Showcase
import dev.matsem.spacenews.shared.feature.articleDetail.model.ArticleDetail
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
private fun Content(
    state: ArticleDetailState,
    actions: ArticleDetailScreen.Actions,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    var bottomBarSize by remember { mutableStateOf(IntSize(0, 0)) }
    val hazeState = rememberHazeState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = actions::onBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = SpaceNewsTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .background(color = SpaceNewsTheme.colorScheme.surface, shape = CircleShape)
                                .padding(Grid.d1_5),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
        bottomBar = {
            val article = (state.article as? LoadingState.Data)?.data
            if (article != null) {
                Button(
                    onClick = {
                        uriHandler.openUri(article.url)
                    },
                    modifier = Modifier.fillMaxWidth()
                        .onSizeChanged { bottomBarSize = it }
                        .hazeEffect(hazeState, HazeMaterials.ultraThin()) {
                            progressive = HazeProgressive.verticalGradient()
                        }
                        .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding, vertical = Grid.d4)
                        .navigationBarsPadding(),
                ) {
                    Text(text = article.ctaButtonText.localized())
                }
            }
        },
    ) {
        DefaultStateLayout(
            modifier = modifier
                .background(SpaceNewsTheme.colorScheme.background),
            loadingState = state.article,
            onRetryClick = actions::onRetryClick,
        ) { article ->
            BoxWithConstraints {
                Column(
                    Modifier
                        .fillMaxSize()
                        .hazeSource(hazeState)
                        .verticalScroll(rememberScrollState()),
                ) {
                    // Header
                    ArticleDetailHeader(
                        article = article,
                        modifier = Modifier.height(this@BoxWithConstraints.maxHeight * 0.8f),
                    )

                    // Content
                    Text(
                        text = article.summary,
                        modifier = Modifier
                            .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding),
                        style = SpaceNewsTheme.typography.bodyLarge,
                        color = SpaceNewsTheme.colorScheme.onBackground,
                    )

                    with(LocalDensity.current) {
                        Spacer(Modifier.size(bottomBarSize.height.toDp()))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ArticleDetailScreenPreview() {
    Showcase {
        Content(
            state = ArticleDetailState(LoadingState.Data(ArticleDetail.mocks().first())),
            actions = ArticleDetailScreen.Actions.noOp(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
