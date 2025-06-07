package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.feature.home.presentation.HomeScreen
import dev.matsem.spacenews.shared.feature.home.presentation.HomeState

@Composable
fun HomeScreenUi(
    homeScreen: HomeScreen,
    modifier: Modifier = Modifier,
) {
    val state by homeScreen.state.collectAsStateWithLifecycle()
    val actions = homeScreen.actions

    Content(state, actions, modifier)
}

@Composable
private fun Content(
    state: HomeState,
    actions: HomeScreen.Actions,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.statusBarsPadding())
        }
        items(state.articles) { article ->
            Column(Modifier.fillMaxWidth()) {
                Text(text = article.title, modifier = Modifier.padding(Grid.d4))
            }
        }
        item {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}
