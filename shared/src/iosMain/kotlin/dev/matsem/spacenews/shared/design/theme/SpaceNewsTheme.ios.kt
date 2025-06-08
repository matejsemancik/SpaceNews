package dev.matsem.spacenews.shared.design.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getColorScheme(isDark: Boolean): ColorScheme = when(isDark) {
    true -> darkColorScheme()
    false -> lightColorScheme()
}
