package dev.matsem.spacenews.shared

import androidx.compose.ui.window.ComposeUIViewController
import dev.matsem.spacenews.shared.feature.Entrypoint

fun MainViewController() = ComposeUIViewController { Entrypoint() }
