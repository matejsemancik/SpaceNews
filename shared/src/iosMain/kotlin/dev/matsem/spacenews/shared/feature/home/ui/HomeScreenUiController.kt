package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.ui.window.ComposeUIViewController
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreen

fun HomeScreenUiController(screen: HomeScreen) = ComposeUIViewController { SpaceNewsTheme { HomeScreenUi(screen) } }
