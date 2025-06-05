package dev.matsem.spacenews.shared.navigation.root

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface RootNavHost : BackHandlerOwner {

    val childStack: StateFlow<ChildStack<RootDestination, RootChild>>
    val actions: Actions

    interface Actions {
        fun navigate(newStack: List<Child<RootDestination, RootChild>>)
        fun pop()
    }
}

@Serializable
sealed interface RootDestination {

    @Serializable
    data object Home : RootDestination

    @Serializable
    data class ArticleDetail(val id: ArticleId) : RootDestination
}

sealed interface RootChild {
    object Home : RootChild
    object ArticleDetail : RootChild
}
