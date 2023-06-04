package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.theme.dimensions
import ru.diploma.appcomponents.core.theme.headerWithoutColor
import ru.diploma.appcomponents.core.theme.regularTextWithoutColor
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.features.imageGallery.R
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import uicomponents.animatedAsyncImage.AnimatedAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    imageId: String,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {

    val uiState by viewModel.detailsScreenUiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getImageDetails(imageId)
    })

    val containerColor = if (uiState.image != null) {
        Color(uiState.image!!.color.toColorInt())
    } else MaterialTheme.colorScheme.background

    Scaffold(containerColor = containerColor,
        topBar = {
            TopAppBar(
                title = { },
                modifier = Modifier.height(MaterialTheme.spacing.x_large),
                navigationIcon = {
                    IconButton(onClick = {
                        navigationManager.navigate(object : NavigationCommand {
                            override val destination: String = NavigationDestination.Back.route
                        })
                    }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                    }
                })
        }) {
        DetailsScreenContent(it, uiState)
    }
}

@Composable
private fun DetailsScreenContent(
    paddingValues: PaddingValues,
    uiState: DetailsScreenUiState
) {
    val containerColor = if (uiState.image != null) {
        Color(uiState.image!!.color.toColorInt())
    } else MaterialTheme.colorScheme.background

    val visibility by remember {
        mutableStateOf(MutableTransitionState(false))
    }

    LaunchedEffect(key1 = Unit, block = {
        visibility.targetState = true
    })

    AnimatedVisibility(
        visibleState = visibility,
        enter = expandHorizontally(clip = false),
        exit = shrinkHorizontally()
    ) {
        Surface(
            color = containerColor,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(MaterialTheme.dimensions.PROGRESS_INDICATOR_SIZE)
                            .align(Alignment.Center),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            } else if (uiState.error.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = uiState.error,
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.headerWithoutColor
                    )
                }
            } else if (uiState.image != null) {
                val image = uiState.image
                SetPhotoAndInfo(paddingValues, containerColor, image)
            }
        }
    }
}

@Composable
private fun SetPhotoAndInfo(
    paddingValues: PaddingValues,
    containerColor: Color,
    image: UnsplashImage
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        AnimatedAsyncImage(
            url = image.urls.imageUrl,
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium)
                .clip(RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)),
            contentScale = ContentScale.Crop
        )
        LikesRow(image = image, paddingValues = paddingValues)
        if (image.description != null) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.6f)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.small),
                    text = image.description,
                    style = MaterialTheme.typography.regularTextWithoutColor,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Justify
                )
            }
        }
        OpenBrowserArtistButton(image = image)
    }
}

@Composable
fun LikesRow(
    image: UnsplashImage,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    Row(
        modifier
            .height(MaterialTheme.spacing.large)
            .fillMaxWidth()
            .alpha(0.6f)
            .padding(start = MaterialTheme.spacing.medium, end = MaterialTheme.spacing.medium)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = "Heart Icon",
            tint = Color.Red
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = image.likes.toString(),
            style = MaterialTheme.typography.regularTextWithoutColor,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun OpenBrowserArtistButton(image: UnsplashImage, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    Button(
        onClick = {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://unsplash.com/@${image.author.authorName}?utm_source=DemoApp&utm_medium=referral")
            )
            startActivity(context, browserIntent, null)
        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
            .alpha(0.6f),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.small,
            vertical = MaterialTheme.spacing.small
        ),
        shape = RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)
    ) {
        Text(
            text = buildAnnotatedString {
                append("Photo by ")
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = FontStyle.Italic
                    )
                ) {
                    append(image.author.authorName)
                }
                append(" on Unsplash")
            },
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.regularTextWithoutColor,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(0.8f),
            maxLines = 1
        )
        Icon(
            imageVector = Icons.Rounded.OpenInBrowser,
            contentDescription = "open artist in browser",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .weight(0.15f)
                .padding(MaterialTheme.spacing.small)
        )
    }
}


