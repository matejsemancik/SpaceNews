package dev.matsem.spacenews.shared.navigation.root

import dev.matsem.spacenews.shared.arch.AppComponentContext

object RootNavHostFactory {
    fun create(componentContext: AppComponentContext): RootNavHost = RootNavHostComponent(componentContext)
}
