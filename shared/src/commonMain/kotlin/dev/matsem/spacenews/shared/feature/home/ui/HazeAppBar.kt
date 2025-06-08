package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun HazeAppBar(
    text: String,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .hazeEffect(hazeState, HazeMaterials.ultraThick()) {
                progressive = HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)
            }
            .background(
                brush = Brush.verticalGradient(
                    0f to SpaceNewsTheme.colorScheme.background,
                    0.5f to SpaceNewsTheme.colorScheme.background,
                    1f to Color.Transparent,
                ),
            ),
    ) {
        Text(
            text = text,
            style = SpaceNewsTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = SpaceNewsTheme.colorScheme.onBackground,
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding, vertical = Grid.d6),
        )
    }
}
