package dev.matsem.spacenews.shared.feature.articleDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.resources.compose.localized
import dev.matsem.spacenews.shared.design.layout.VerticalSpacer
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.feature.articleDetail.model.ArticleDetail

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ArticleDetailHeader(
    article: ArticleDetail,
    modifier: Modifier = Modifier,
) {
    val hazeState = rememberHazeState()

    Box(modifier) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
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
}
