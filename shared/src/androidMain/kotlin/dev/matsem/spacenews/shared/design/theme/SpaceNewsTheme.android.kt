package dev.matsem.spacenews.shared.design.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getColorScheme(isDark: Boolean): ColorScheme {
    val dynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    return when {
        dynamicColorSupported && isDark -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColorSupported && !isDark -> dynamicLightColorScheme(LocalContext.current)
        isDark -> darkColorScheme()
        else -> lightColorScheme()
    }
}
