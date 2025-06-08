package dev.matsem.spacenews.shared.design.image

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.matsem.spacenews.shared.design.theme.Grid
import dev.matsem.spacenews.shared.design.theme.SpaceNewsTheme

@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val sizeResolver = rememberConstraintsSizeResolver()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .crossfade(true)
            .size(sizeResolver)
            .build(),
    )

    val state by painter.state.collectAsStateWithLifecycle()
    AnimatedContent(
        targetState = state,
        modifier = modifier.then(sizeResolver),
        contentAlignment = Alignment.Center,
    ) { state ->
        Box {
            when (state) {
                AsyncImagePainter.State.Empty -> Unit
                is AsyncImagePainter.State.Error -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SpaceNewsTheme.colorScheme.surfaceContainer),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Error,
                            contentDescription = null,
                            modifier = Modifier.size(Grid.d6),
                            tint = SpaceNewsTheme.colorScheme.error
                        )
                    }
                }

                is AsyncImagePainter.State.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SpaceNewsTheme.colorScheme.surfaceContainer),
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(Grid.d6),
                            color = SpaceNewsTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                is AsyncImagePainter.State.Success -> Image(
                    painter = painter,
                    contentScale = contentScale,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
