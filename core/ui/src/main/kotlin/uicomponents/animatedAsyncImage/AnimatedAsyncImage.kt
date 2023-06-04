package uicomponents.animatedAsyncImage

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlin.math.min

@Composable
fun AnimatedAsyncImage(url: String, modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.Fit){
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    val state = painter.state

    val transition by animateFloatAsState(
        targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        LoadingAnimation()
    }
    Image(
        painter = painter,
        contentDescription = "custom transition based on painter state",
        modifier = modifier
            .scale(.8f + (.2f * transition))
            .alpha(min(1f, transition / .2f))
            .alpha(transition),
        contentScale = contentScale,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
    )
}