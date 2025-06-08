package dev.matsem.spacenews.shared.feature.articleDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.resources.compose.localized
import dev.matsem.spacenews.shared.arch.state.LoadingState
import dev.matsem.spacenews.shared.design.layout.DefaultStateLayout
import dev.matsem.spacenews.shared.design.layout.VerticalSpacer
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
    val hazeState = rememberHazeState()
    val uriHandler = LocalUriHandler.current

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
                        .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding, vertical = Grid.d4)
                        .navigationBarsPadding(),
                ) {
                    Text(text = "Read online")
                }
            }
        },
    ) {
        DefaultStateLayout(
            modifier = modifier.background(SpaceNewsTheme.colorScheme.background),
            loadingState = state.article,
            onRetryClick = actions::onRetryClick,
        ) { article ->
            BoxWithConstraints {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    // Header box
                    Box {
                        AsyncImage(
                            model = article.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(this@BoxWithConstraints.maxHeight / 2.5f)
                                .hazeSource(hazeState),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .hazeEffect(
                                    state = hazeState,
                                    style = HazeMaterials.ultraThick(),
                                ) {
                                    progressive = HazeProgressive.verticalGradient()
                                }
                                .background(
                                    brush = Brush.verticalGradient(
                                        0f to Color.Transparent,
                                        0.8f to SpaceNewsTheme.colorScheme.background,
                                        1f to SpaceNewsTheme.colorScheme.background,
                                    ),
                                ),
                        ) {
                            VerticalSpacer(Grid.d20)

                            Text(
                                text = article.title.localized(),
                                style = SpaceNewsTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = SpaceNewsTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding),
                            )

                            Text(
                                text = article.date.localized(),
                                style = SpaceNewsTheme.typography.labelLarge,
                                color = SpaceNewsTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding),
                            )

                            VerticalSpacer(Grid.d6)
                        }
                    }

                    // Content
                    Text(
                        text = article.summary,
                        modifier = Modifier
                            .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding),
                        style = SpaceNewsTheme.typography.bodyMedium,
                        color = SpaceNewsTheme.colorScheme.onBackground,
                    )
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
