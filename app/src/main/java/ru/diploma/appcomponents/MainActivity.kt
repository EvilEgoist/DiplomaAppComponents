package ru.diploma.appcomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.diploma.appcomponents.ui.theme.*
import ru.diploma.appcomponents.ui.model.CardModel
import ru.diploma.appcomponents.ui.model.EntertainmentModel
import ru.diploma.appcomponents.ui.model.EntertainmentType
import ru.diploma.appcomponents.ui.model.ModelsRepo
import kotlin.text.Typography


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DiplomaAppComponentsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val state = rememberLazyListState()
                    LazyRow(
                        state = state,
                        modifier = Modifier.fillMaxWidth(),
                        flingBehavior = rememberSnapFlingBehavior(state)
                    ) {
                        items(ModelsRepo.getCards()) { card ->
                            EntertainmentCard(model = card)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(
    showBackground = true,
    backgroundColor = 0xFFf1f3f3,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    DiplomaAppComponentsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val state = rememberLazyListState()
            LazyRow(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                flingBehavior = rememberSnapFlingBehavior(state)
            ) {
                items(ModelsRepo.getCards()) { card ->
                    EntertainmentCard(model = card)
                }
            }
        }
    }
}

@Composable
fun EntertainmentCard(model: CardModel, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(percent = 8),
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .height(IntrinsicSize.Min)
            .width(MaterialTheme.dimensions.CARD_WIDTH)
    ) {
        Box(
            modifier =
            Modifier.background(getGradient(model.firstColor, model.secondColor))
        ) {
            CardContent(
                model.entertainmentModel,
                Modifier.padding(MaterialTheme.spacing.medium)
            )
        }
    }
}

@Composable
fun CardContent(entertainmentModel: EntertainmentModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        CardHeader(entertainmentModel)
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.semi_large))
        Text(
            text = entertainmentModel.mainText,
            style = Typography.bigLightHeader
        )
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.semi_large))
        Place(entertainmentModel = entertainmentModel)
    }
}

@Composable
fun Place(entertainmentModel: EntertainmentModel) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        CardImage(imageUrl = entertainmentModel.logoUrl)
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.semi_large))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = entertainmentModel.name,
                style = Typography.mediumHeader,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Text(
                text = entertainmentModel.address,
                style = Typography.cardDate,
            )
        }
    }
}

@Composable
fun CardImage(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(id = R.drawable.placeholder),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1.0f)
            .clip(RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE))
    )
}

fun getGradient(color1: Long, color2: Long): Brush {
    return Brush.linearGradient(
        listOf(Color(color1), Color(color2))
    )
}

fun parseIconType(type: EntertainmentType): Int {
    return when (type) {
        EntertainmentType.RESTAURANT -> R.drawable.ic_restaurant
        EntertainmentType.BAR -> R.drawable.ic_bar
        EntertainmentType.CINEMA -> R.drawable.ic_cinema
        EntertainmentType.MUSEUM -> R.drawable.ic_museum
        EntertainmentType.THEATER -> R.drawable.ic_theatre
        EntertainmentType.PARK -> R.drawable.ic_park
        EntertainmentType.OTHER -> R.drawable.ic_ticket
    }
}

@Composable
fun EntertainmentIcon(type: EntertainmentType, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(parseIconType(type)),
        contentDescription = null,
        modifier = modifier
            .size(LocalDimensions.current.MEDIUM_ICON)
            .clip(RoundedCornerShape(percent = 100)),
        tint = MaterialTheme.colorScheme.background
    )
}

@Composable
fun RowScope.ArrowIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_arrow_right),
        modifier = Modifier
            .size(LocalDimensions.current.SMALL_ICON)
            .align(Alignment.CenterVertically),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.background,
    )
}

@Composable
fun DateTimeHeader(time: String, date: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = time,
            style = Typography.lightHeader,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = date,
            style = Typography.cardDate,
        )
    }
}


@Composable
fun CardHeader(entertainmentModel: EntertainmentModel, modifier: Modifier = Modifier) {
    Row {
        EntertainmentIcon(type = entertainmentModel.type)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        DateTimeHeader(
            entertainmentModel.time,
            entertainmentModel.date,
            Modifier
                .width(IntrinsicSize.Max)
                .weight(0.6f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(0.4f))
        ArrowIcon()
    }
}