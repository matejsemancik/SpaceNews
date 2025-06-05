package dev.matsem.spacenews.shared.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.extensions.compose.stack.animation.LocalStackAnimationProvider

@Composable
fun SpaceNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    CompositionLocalProvider(
        LocalStackAnimationProvider provides SpaceNewsStackAnimationProvider,
    ) {
        MaterialTheme(
            colorScheme = colors,
            content = content,
        )
    }
}
