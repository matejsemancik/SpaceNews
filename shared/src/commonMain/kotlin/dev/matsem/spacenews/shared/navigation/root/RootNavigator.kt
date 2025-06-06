package dev.matsem.spacenews.shared.navigation.root

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import dev.matsem.spacenews.shared.data.repo.model.ArticleId
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailComponent
import dev.matsem.spacenews.shared.feature.articleDetail.presentation.ArticleDetailNavigation
import dev.matsem.spacenews.shared.feature.home.presentation.HomeNavigation
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreen

/**
 * Navigator that handles navigation between screens in the root of the application.
 *
 * Implements [HomeNavigation] and [ArticleDetailNavigation] to provide navigation capabilities
 * to the respective screens.
 */
internal class RootNavigator(val decomposeNavigator: StackNavigation<RootDestination> = StackNavigation()) :
    StackNavigation<RootDestination> by decomposeNavigator,
    HomeNavigation,
    ArticleDetailNavigation {

    fun navigate(newStack: List<Child<RootDestination, RootChild>>) =
        navigate(transformer = { newStack.map { child -> child.configuration } })

    override fun HomeScreen.navigateToArticleDetail(id: ArticleId) =
        pushNew(configuration = RootDestination.ArticleDetail(id))

    override fun ArticleDetailComponent.navigateBack() = pop()
}
