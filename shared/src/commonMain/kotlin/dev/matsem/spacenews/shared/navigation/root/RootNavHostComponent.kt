package dev.matsem.spacenews.shared.navigation.root

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import dev.matsem.spacenews.shared.arch.AppComponentContext
import dev.matsem.spacenews.shared.arch.BaseComponent
import dev.matsem.spacenews.shared.arch.asStateFlow
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailScreenFactory
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreenFactory
import kotlinx.coroutines.flow.StateFlow

internal class RootNavHostComponent(componentContext: AppComponentContext) : BaseComponent<RootNavHostState, Nothing>(
    componentContext = componentContext,
    defaultState = RootNavHostState,
), RootNavHost {

    private val navigator = RootNavigator()

    override val childStack: StateFlow<ChildStack<RootDestination, RootChild>> = childStack(
        source = navigator,
        serializer = RootDestination.serializer(),
        initialStack = { listOf(RootDestination.Home) },
        handleBackButton = true,
    ) { destination, childContext ->
        when (destination) {
            is RootDestination.ArticleDetail -> RootChild.ArticleDetail(
                screen = ArticleDetailScreenFactory.create(componentContext = childContext, navigation = navigator),
            )

            RootDestination.Home -> RootChild.Home(
                screen = HomeScreenFactory.create(componentContext = childContext, navigation = navigator),
            )
        }
    }.asStateFlow()

    override val actions: RootNavHost.Actions = object : RootNavHost.Actions {

        override fun navigate(newStack: List<Child<RootDestination, RootChild>>) =
            navigator.navigate(transformer = { newStack.map { child -> child.configuration } })

        override fun pop() = navigator.pop()
    }
}
