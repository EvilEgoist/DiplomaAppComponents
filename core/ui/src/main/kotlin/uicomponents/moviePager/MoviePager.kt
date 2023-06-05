package uicomponents.moviePager

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import ru.diploma.appcomponents.core.theme.spacing
import uicomponents.utils.offsetForPage
import uicomponents.utils.startOffsetForPage
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviePager() {

    val horizontalState = rememberPagerState(initialPage = 0)

    Column {
        HorizontalPager(
            pageCount = movies.size,
            modifier = Modifier
                .weight(.7f)
                .padding(
                    top = MaterialTheme.spacing.x_large
                ),
            state = horizontalState,
            pageSpacing = 1.dp,
            beyondBoundsPageCount = 9,
        ) { page ->
            Box(
                modifier = Modifier
                    .zIndex(page * 10f)
                    .padding(
                        start = 64.dp,
                        end = 32.dp,
                    )
                    .graphicsLayer {
                        val startOffset = horizontalState.startOffsetForPage(page)
                        translationX = size.width * (startOffset * .99f)

                        alpha = (2f - startOffset) / 2f

                        val blur = (startOffset * 20f).coerceAtLeast(0.1f)
                        renderEffect = RenderEffect
                            .createBlurEffect(
                                blur, blur, Shader.TileMode.DECAL
                            )
                            .asComposeRenderEffect()

                        val scale = 1f - (startOffset * .1f)
                        scaleX = scale
                        scaleY = scale
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        color = Color(0xFFF58133),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = movies[page].img,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(.3f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val verticalState = rememberPagerState()

            VerticalPager(
                pageCount = movies.size,
                state = verticalState,
                modifier = Modifier
                    .weight(1f)
                    .height(72.dp),
                userScrollEnabled = false,
                horizontalAlignment = Alignment.Start,
            ) { page ->
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = movies[page].title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Thin,
                            fontSize = 28.sp,
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = movies[page].subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            LaunchedEffect(Unit) {
                snapshotFlow {
                    Pair(
                        horizontalState.currentPage,
                        horizontalState.currentPageOffsetFraction
                    )
                }.collect { (page, offset) ->
                    verticalState.scrollToPage(page, offset)
                }
            }


            val interpolatedRating by remember {
                derivedStateOf {
                    val position = horizontalState.offsetForPage(0)
                    val from = floor(position).roundToInt()
                    val to = ceil(position).roundToInt()

                    val fromRating = movies[from].rating.toFloat()
                    val toRating = movies[to].rating.toFloat()

                    val fraction = position - position.toInt()
                    fromRating + ((toRating - fromRating) * fraction)
                }
            }

            RatingStars(rating = interpolatedRating)
        }
    }
}

@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    rating: Float,
) {
    Row(
        modifier = modifier
    ) {

        for (i in 1..5) {
            val animatedScale by animateFloatAsState(
                targetValue = if (floor(rating) >= i) {
                    1f
                } else if (ceil(rating) < i) {
                    0f
                } else {
                    rating - rating.toInt()
                },
                animationSpec = spring(
                    stiffness = Spring.StiffnessMedium
                ),
                label = ""
            )

            Box(
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.Star),
                    contentDescription = null,
                    modifier = Modifier.alpha(.1f),
                )
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.Star),
                    contentDescription = null,
                    modifier = Modifier.scale(animatedScale),
                    tint = Color(0xFFD59411)
                )
            }

        }

    }
}


data class Movie(
    val title: String = "Avengers",
    val subtitle: String = "",
    val rating: Int = 4,
    val img: String = "",
)

val movies = listOf(
    Movie(
        title = "The Lobster",
        subtitle = "Yorgos Lanthimos • 2015",
        rating = 2,
        img = "https://www.game-ost.ru/static/covers_soundtracks/8/0/80898_942063.jpg",
    ),
    Movie(
        title = "Her",
        subtitle = "Spike Jonze • 2013",
        rating = 4,
        img = "https://buhurt.ru/data/img/covers/films/51.webp?hash=cfac1738becf10cbec608436e3f69881",
    ),
    Movie(
        title = "Pulp fiction",
        subtitle = "Quentin Tarantino • 1994",
        rating = 5,
        img = "https://i.pinimg.com/originals/eb/d7/ab/ebd7ab4c40764179bb28f25a16d780ab.jpg",
    ),
    Movie(
        title = "The Room",
        subtitle = "Tommy Wiseau • 2003",
        rating = 1,
        img = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/d01dc4ce-2247-4e8f-8f42-b43fe854c606/3840x",
    ),
    Movie(
        title = "Moonlight",
        subtitle = "Barry Jenkins • 2016",
        rating = 4,
        img = "https://r1.ilikewallpaper.net/ipad-pro-wallpapers/download/37900/Moonlight-film-best-illustration-art-ipad-pro-wallpaper-ilikewallpaper_com.jpg",
    ),
    Movie(
        title = "Little Miss Sunshine",
        subtitle = "Dayton & Faris • 2006",
        rating = 5,
        img = "https://i.pinimg.com/originals/f1/55/97/f15597656a394a399da453b77bdcaf7f.jpg",
    ),
)