package dev.matsem.spacenews.shared.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import dev.icerock.moko.resources.compose.localized
import dev.matsem.spacenews.shared.design.image.NetworkImage
import dev.matsem.spacenews.shared.design.layout.HorizontalSpacer
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme
import dev.matsem.spacenews.shared.design.tooling.Showcase
import dev.matsem.spacenews.shared.feature.home.model.ArticleListItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ArticleListRow(
    article: ArticleListItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = SpaceNewsTheme.dimensions.horizontalContentPadding, vertical = Grid.d3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            url = article.thumbnailUrl,
            contentDescription = null,
            modifier = Modifier
                .size(width = Grid.d22, height = Grid.d16)
                .clip(SpaceNewsTheme.shapes.small),
            contentScale = ContentScale.Crop,
        )
        HorizontalSpacer(Grid.d4)
        Column {
            Text(
                text = article.date.localized(),
                style = SpaceNewsTheme.typography.labelMedium,
                color = SpaceNewsTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = article.title.localized(),
                style = SpaceNewsTheme.typography.bodyLarge,
                color = SpaceNewsTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
@Preview
private fun ArticleListItemRowPreview() {
    Showcase {
        ArticleListRow(article = ArticleListItem.mocks().first(), Modifier.fillMaxWidth(), onClick = {})
    }
}
