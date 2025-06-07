package dev.matsem.spacenews.shared.navigation.root

import dev.matsem.spacenews.shared.arch.presentation.AppComponentContext

object RootNavHostFactory {
    fun create(componentContext: AppComponentContext): RootNavHost = RootNavHostComponent(componentContext)
}
