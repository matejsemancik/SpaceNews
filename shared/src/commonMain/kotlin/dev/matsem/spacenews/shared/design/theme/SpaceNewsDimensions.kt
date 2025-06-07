package dev.matsem.spacenews.shared.design.theme

import androidx.compose.ui.unit.Dp

data class SpaceNewsDimensions(
    val horizontalContentPadding: Dp,
)

fun mobileSpaceNewsDimensions() = SpaceNewsDimensions(
    horizontalContentPadding = Grid.d4,
)
