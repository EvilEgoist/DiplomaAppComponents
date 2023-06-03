package ru.diploma.appcomponents.imageGallery.presentation.composable

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.diploma.appcomponents.core.theme.dimensions
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.features.imageGallery.R
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import uicomponents.animatedAsyncImage.AnimatedAsyncImage
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
fun ListContent(items: LazyPagingItems<UnsplashImage>, onClickAction: (String) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(tween(500)),
        contentPadding = PaddingValues(all = MaterialTheme.spacing.small),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = items.itemCount,
//            key = items.itemKey(key = { unsplashImage ->
//                unsplashImage.id
//            }),
            contentType = items.itemContentType(
            )
        ) { index ->
            val visibility by remember {
                mutableStateOf(MutableTransitionState(false))
            }

            LaunchedEffect(key1 = Unit, block = {
                visibility.targetState = true
            })

            AnimatedVisibility(
                visibleState = visibility,
                enter = expandHorizontally(clip = false) + scaleIn(),
                exit = shrinkHorizontally() + scaleOut()
            ) {
                val item = items[index]
                item?.let { UnsplashItem(unsplashImage = it, onClickAction = onClickAction) }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun UnsplashItem(unsplashImage: UnsplashImage, onClickAction: (String) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .clickable {
//                val browserIntent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://unsplash.com/@${unsplashImage.author.authorName}?utm_source=DemoApp&utm_medium=referral")
//                )
//                startActivity(context, browserIntent, null)
                onClickAction(unsplashImage.id)
            }
            .clip(RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE))
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(unsplashImage.urls.imageUrl)
//                .crossfade(true)
//                .build(),
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)),
//            contentDescription = "Unsplash Image",
//            //contentScale = ContentScale.Crop
//        )
        AnimatedAsyncImage(url = unsplashImage.urls.imageUrl)
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {}
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Photo by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                        append(unsplashImage.author.authorName)
                    }
                    append(" on Unsplash")
                },
                color = Color.White,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LikeCounter(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_heart),
                likes = "${unsplashImage.likes}"
            )
        }
    }
}

@Composable
fun LikeCounter(
    modifier: Modifier,
    painter: Painter,
    likes: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painter,
            contentDescription = "Heart Icon",
            tint = Color.Red
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = likes,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}