package dev.matsem.spacenews.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
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

    Scaffold(modifier, contentWindowInsets = WindowInsets.navigationBars) { paddingValues ->
        Children(
            stack = stack,
            modifier = Modifier.padding(paddingValues),
            animation = predictiveBackAnimation(
                backHandler = rootNavHost.backHandler,
                onBack = actions::pop,
                selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
            ),
        ) { child ->
            when (val instance = child.instance) {
                is RootChild.Home -> Surface(Modifier.fillMaxSize()) { Text("Home") }
                is RootChild.ArticleDetail -> Surface(Modifier.fillMaxSize()) { Text("ArticleDetail") }
            }
        }
    }
}
