package dev.matsem.spacenews.shared

import dev.matsem.spacenews.shared.injection.SpaceNewsInjection
import org.koin.dsl.KoinAppDeclaration

object SpaceNewsApp {

    fun initializeSharedFramework(appDeclaration: KoinAppDeclaration? = null) {
        SpaceNewsInjection.initialize(appDeclaration)
    }
}
