package dev.matsem.spacenews.shared.navigation.root

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailScreen
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

    @OptIn(ExperimentalUuidApi::class)
    @Serializable
    data class ArticleDetail(
        val id: ArticleId,

        /*
        Random UUID ensures uniqueness of navigation entries on child stack. This allows stack to contain multiple instances of the same ArticleDetail.
        Although unlikely, there can be case when user opens multiple instances of the same ArticleDetail by doing a "recommended articles" loop.
        This app does not contain such functionality (yet), but defensive approach did never hurt anybody.
        */
        val seed: String = Uuid.random().toHexString(),
    ) : RootDestination
}

sealed interface RootChild {
    data class Home(val screen: HomeScreen) : RootChild
    data class ArticleDetail(val screen: ArticleDetailScreen) : RootChild
}
