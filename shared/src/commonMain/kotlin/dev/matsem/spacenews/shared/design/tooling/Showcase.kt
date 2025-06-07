package dev.matsem.spacenews.shared.design.tooling

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme

@Composable
fun Showcase(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    SpaceNewsTheme(isDark) {
        Surface(color = SpaceNewsTheme.colorScheme.background) {
            content()
        }
    }
}
