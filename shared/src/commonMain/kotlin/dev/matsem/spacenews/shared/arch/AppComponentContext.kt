package dev.matsem.spacenews.shared.arch

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.decompose.GenericComponentContext
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.statekeeper.StateKeeperOwner

/**
 * App-specific [com.arkivanov.decompose.ComponentContext] which can be decorated with custom functionality in the future should it
 * be required.
 */
interface AppComponentContext : GenericComponentContext<AppComponentContext> {
    companion object
}

/**
 * Default implementation of [AppComponentContext].
 */
internal class DefaultAppComponentContext(componentContext: ComponentContext) :
    AppComponentContext,
    LifecycleOwner by componentContext,
    StateKeeperOwner by componentContext,
    InstanceKeeperOwner by componentContext,
    BackHandlerOwner by componentContext {

    override val componentContextFactory: ComponentContextFactory<AppComponentContext> =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            DefaultAppComponentContext(componentContext.componentContextFactory(lifecycle, stateKeeper, instanceKeeper, backHandler))
        }
}

/**
 * Default implementation of [AppComponentContext]. Can be used as a context factory for the top-level
 * components.
 */
fun defaultAppComponentContext(context: ComponentContext): AppComponentContext = DefaultAppComponentContext(context)
