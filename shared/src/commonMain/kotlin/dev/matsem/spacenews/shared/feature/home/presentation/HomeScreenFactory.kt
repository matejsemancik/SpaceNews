package dev.matsem.spacenews.shared.feature.home.presentation

import dev.matsem.spacenews.shared.arch.presentation.AppComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal object HomeScreenFactory : KoinComponent {

    fun create(componentContext: AppComponentContext, navigation: HomeNavigation): HomeComponent {
        return get(parameters = { parametersOf(componentContext, navigation) })
    }
}
