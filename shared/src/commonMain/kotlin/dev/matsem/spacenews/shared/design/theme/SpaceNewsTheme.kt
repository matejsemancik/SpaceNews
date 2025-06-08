package dev.matsem.spacenews.shared.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.extensions.compose.stack.animation.LocalStackAnimationProvider

// Returns dynamic color scheme on Android if supported
@Composable
expect fun getColorScheme(isDark: Boolean): ColorScheme

@Composable
fun SpaceNewsTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    dimensions: SpaceNewsDimensions = mobileSpaceNewsDimensions(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = getColorScheme(isDark),
    ) {
        CompositionLocalProvider(
            LocalSpaceNewsDimensions provides dimensions,
            LocalStackAnimationProvider provides SpaceNewsStackAnimationProvider,
        ) {
            content()
        }
    }
}

object SpaceNewsTheme {

    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    val dimensions: SpaceNewsDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaceNewsDimensions.current
}

val LocalSpaceNewsDimensions = staticCompositionLocalOf<SpaceNewsDimensions> { error("SpaceNewsDimensions not provided") }
