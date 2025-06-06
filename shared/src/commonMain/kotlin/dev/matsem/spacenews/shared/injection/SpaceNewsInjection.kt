package dev.matsem.spacenews.shared.injection

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal object SpaceNewsInjection {

    fun initialize(appDeclaration: KoinAppDeclaration?) {
        startKoin {
            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                componentModule(),
                networkModule(),
                repositoryModule(),
            )
        }
    }
}
