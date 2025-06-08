package dev.matsem.spacenews.shared.design.layout

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.matsem.spacenews.resources.MR
import dev.matsem.spacenews.shared.arch.state.LoadingState
import dev.matsem.spacenews.shared.arch.state.UiError
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Default implementations for loading and error states in the StateLayout.
 */
object StateLayoutDefaults {

    /**
     * Displays a centered loading indicator.
     * 
     * @param modifier Modifier to be applied to the loading indicator container
     */
    @Composable
    fun Loading(modifier: Modifier = Modifier) = Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(Modifier.size(Grid.d8))
    }

    /**
     * Displays an error message with a retry button.
     * 
     * @param uiError The error to display
     * @param onRetryClick Callback to be invoked when the retry button is clicked
     * @param modifier Modifier to be applied to the error container
     */
    @Composable
    fun Error(uiError: UiError, onRetryClick: () -> Unit, modifier: Modifier = Modifier) = Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = uiError.title.localized(),
            textAlign = TextAlign.Center,
            style = SpaceNewsTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        )
        VerticalSpacer(Grid.d1)
        Text(
            text = uiError.message.localized(),
            textAlign = TextAlign.Center,
            style = SpaceNewsTheme.typography.labelLarge,
        )
        VerticalSpacer(Grid.d3)
        Button(onClick = onRetryClick) {
            Text(text = stringResource(MR.strings.generic_retry))
        }
    }
}

/**
 * A composable that handles different states of data loading with animations.
 * 
 * @param T The type of data being loaded
 * @param loadingState The current loading state
 * @param modifier Modifier to be applied to the layout
 * @param loading Composable to be shown during loading
 * @param error Composable to be shown when an error occurs
 * @param content Composable to be shown when data is successfully loaded
 */
@Composable
fun <T : Any> StateLayout(
    loadingState: LoadingState<T>,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit,
    error: @Composable (error: UiError) -> Unit,
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(
        targetState = loadingState,
        modifier = modifier.background(SpaceNewsTheme.colorScheme.background),
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        contentKey = { state -> state::class },
    ) { state ->
        Box {
            when (state) {
                is LoadingState.Data<T> -> content(state.data)
                is LoadingState.Error -> error(state.error)
                LoadingState.Loading -> loading()
            }
        }
    }
}

/**
 * A convenience wrapper around StateLayout that uses default loading and error states.
 * 
 * @param T The type of data being loaded
 * @param loadingState The current loading state
 * @param modifier Modifier to be applied to the layout
 * @param onRetryClick Callback to be invoked when the retry button is clicked
 * @param content Composable to be shown when data is successfully loaded
 */
@Composable
fun <T : Any> DefaultStateLayout(
    loadingState: LoadingState<T>,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
    content: @Composable (T) -> Unit,
) {
    StateLayout(
        loadingState = loadingState,
        modifier = modifier,
        loading = { StateLayoutDefaults.Loading(Modifier.fillMaxSize()) },
        error = { uiError -> StateLayoutDefaults.Error(uiError, onRetryClick, Modifier.fillMaxSize()) },
        content = content,
    )
}

@Preview
@Composable
private fun LoadingPreview() {
    val state: LoadingState<String> = LoadingState.Loading
    DefaultStateLayout(loadingState = state, onRetryClick = {}) { data ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(data)
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    val state: LoadingState<String> = LoadingState.Error(UiError.connection())
    DefaultStateLayout(loadingState = state, onRetryClick = {}) { data ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(data)
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    DefaultStateLayout(loadingState = LoadingState.Data("This is content"), onRetryClick = {}) { data ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(data)
        }
    }
}
