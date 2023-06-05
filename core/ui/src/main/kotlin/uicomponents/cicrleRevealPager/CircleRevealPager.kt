package uicomponents.cicrleRevealPager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import ru.diploma.appcomponents.core.theme.spacing
import uicomponents.utils.endOffsetForPage
import uicomponents.utils.offsetForPage
import uicomponents.utils.startOffsetForPage
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CircleRevealPager(itemsList: List<Any>, item: @Composable (Int) -> Unit) {
    val state = rememberPagerState()
    var offsetY by remember { mutableStateOf(0f) }
    HorizontalPager(
        pageCount = itemsList.size,
        modifier = Modifier
            .pointerInteropFilter {
                offsetY = it.y
                false
            }
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.large
            )
            .clip(
                RoundedCornerShape(25.dp)
            )
            .background(Color.Black),
        state = state,
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = state.offsetForPage(page)
                    translationX = size.width * pageOffset

                    val endOffset = state.endOffsetForPage(page)

                    shadowElevation = 20f
                    shape = CirclePath(
                        progress = 1f - endOffset.absoluteValue,
                        origin = Offset(
                            size.width,
                            offsetY,
                        )
                    )
                    clip = true

                    val absoluteOffset = state.offsetForPage(page).absoluteValue
                    val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                    scaleX = scale
                    scaleY = scale

                    val startOffset = state.startOffsetForPage(page)
                    alpha = (2f - startOffset) / 2f

                },
            contentAlignment = Alignment.Center,
        ) {
            item(page)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LazyItemsCircleReveal(itemsList: LazyPagingItems<out Any>, item: @Composable (Int) -> Unit) {
    val state = rememberPagerState()
    var offsetY by remember { mutableStateOf(0f) }
    HorizontalPager(
        pageCount = itemsList.itemCount,
        modifier = Modifier
            .pointerInteropFilter {
                offsetY = it.y
                false
            }
            .padding(
                start = MaterialTheme.spacing.large,
                end = MaterialTheme.spacing.large,
                top = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.large
            )
            .clip(
                RoundedCornerShape(25.dp)
            )
            .background(Color.Black),
        state = state,
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = state.offsetForPage(page)
                    translationX = size.width * pageOffset

                    val endOffset = state.endOffsetForPage(page)

                    shadowElevation = 20f
                    shape = CirclePath(
                        progress = 1f - endOffset.absoluteValue,
                        origin = Offset(
                            size.width,
                            offsetY,
                        )
                    )
                    clip = true

                    val absoluteOffset = state.offsetForPage(page).absoluteValue
                    val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                    scaleX = scale
                    scaleY = scale

                    val startOffset = state.startOffsetForPage(page)
                    alpha = (2f - startOffset) / 2f

                },
            contentAlignment = Alignment.Center,
        ) {
            item(page)
        }
    }
}

class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {

        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress

        return Outline.Generic(Path().apply {
            addOval(
                Rect(
                    center = center,
                    radius = radius,
                )
            )
        })
    }
}
