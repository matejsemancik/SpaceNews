package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import dev.matsem.spacenews.shared.arch.state.UiError
import dev.matsem.spacenews.shared.design.layout.VerticalSpacer
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.design.tooling.Showcase
import dev.matsem.spacenews.shared.feature.home.model.LoadingFooterItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingFooterRow(
    item: LoadingFooterItem,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Box(modifier) {
        when (item) {
            is LoadingFooterItem.Loading -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Grid.d4),
            ) {
                CircularProgressIndicator(Modifier.size(Grid.d7))
            }

            is LoadingFooterItem.Error -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Grid.d4),
            ) {
                val error = item.error
                Text(text = error.title.localized(), textAlign = TextAlign.Center, style = SpaceNewsTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
                VerticalSpacer(Grid.d1)
                Text(text = error.message.localized(), textAlign = TextAlign.Center, style = SpaceNewsTheme.typography.labelLarge)
                VerticalSpacer(Grid.d3)
                Button(onClick = onRetryClick) {
                    Text(text = stringResource(MR.strings.generic_retry))
                }
            }
        }
    }
}

@Composable
@Preview
private fun LoadingFooterRowPreviewLoading() {
    Showcase {
        LoadingFooterRow(
            item = LoadingFooterItem.Loading,
            modifier = Modifier.fillMaxWidth(),
            onRetryClick = {},
        )
    }
}

@Composable
@Preview
private fun LoadingFooterRowPreviewLoadingError() {
    Showcase {
        LoadingFooterRow(
            item = LoadingFooterItem.Error(UiError.connection()),
            modifier = Modifier.fillMaxWidth(),
            onRetryClick = {},
        )
    }
}
