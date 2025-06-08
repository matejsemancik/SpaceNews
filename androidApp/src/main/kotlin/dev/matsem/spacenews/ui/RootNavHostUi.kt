package dev.matsem.spacenews.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import dev.matsem.spacenews.shared.feature.articleDetail.ui.ArticleDetailScreenUi
import dev.matsem.spacenews.shared.feature.home.ui.HomeScreenUi
import dev.matsem.spacenews.shared.navigation.root.RootChild
import dev.matsem.spacenews.shared.navigation.root.RootNavHost

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootNavHostUi(
    rootNavHost: RootNavHost,
    modifier: Modifier = Modifier,
) {
    val stack by rootNavHost.childStack.collectAsStateWithLifecycle()
    val actions = rootNavHost.actions

    Children(
        modifier = modifier,
        stack = stack,
        animation = predictiveBackAnimation(
            backHandler = rootNavHost.backHandler,
            onBack = actions::pop,
            selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
        ),
    ) { child ->
        when (val instance = child.instance) {
            is RootChild.Home -> HomeScreenUi(homeScreen = instance.screen, modifier = Modifier.fillMaxSize())
            is RootChild.ArticleDetail -> ArticleDetailScreenUi(articleDetailScreen = instance.screen, modifier = Modifier.fillMaxSize())
        }
    }
}
