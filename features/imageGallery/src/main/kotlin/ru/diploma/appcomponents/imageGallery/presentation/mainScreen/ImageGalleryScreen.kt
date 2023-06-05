package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.imageGallery.domain.model.UnsplashImage
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import ru.diploma.appcomponents.imageGallery.presentation.composable.SortOrderMenu
import uicomponents.animatedAsyncImage.AnimatedAsyncImage
import uicomponents.cicrleRevealPager.CirclePath
import uicomponents.cicrleRevealPager.CircleRevealPager
import uicomponents.cicrleRevealPager.LazyItemsCircleReveal
import uicomponents.moviePager.MoviePager
import uicomponents.utils.endOffsetForPage
import uicomponents.utils.offsetForPage
import uicomponents.utils.startOffsetForPage
import kotlin.math.absoluteValue


@Composable
fun ImageGalleryRoute(
    viewModel: ImageGalleryViewModel = hiltViewModel(),
    navigationManager: NavigationManager,
) {

    var imageItems = viewModel.images.collectAsLazyPagingItems()

    val sortOrderState = viewModel.sortOrderFlow.collectAsStateWithLifecycle()
    val currentSortOrder by remember { sortOrderState }

    val visibility by remember {
        mutableStateOf(MutableTransitionState(false))
    }

    LaunchedEffect(key1 = Unit, block = {
        visibility.targetState = true
    })

    AnimatedVisibility(
        visibleState = visibility,
        enter = expandHorizontally(clip = false) + scaleIn(),
        exit = shrinkHorizontally()
    ) {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(Modifier.fillMaxSize()) {
                mainScreenSearchBar(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)) {
                    navigationManager.navigate(object : NavigationCommand {
                        override val destination: String = NavigationDestination.SearchImages.route
                    })
                }
                Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
                SortOrderMenu(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = MaterialTheme.spacing.small),
                    currentSortOrder = currentSortOrder,
                    onItemClick = {
                        viewModel.changeSortOrder(it)
                    })
                Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
//                ListContent(items = imageItems) {
//                    navigationManager.navigate(object : NavigationCommand {
//                        override val destination: String =
//                            NavigationDestination.ImageDetails.route + it
//                    })
//                }
                LazyItemsCircleReveal(imageItems) {
                    if (imageItems.itemCount > 0) {
                        imageItems[it]?.let { it1 ->
                            CircleRevealItem(unsplashImage = it1, onClick = { imageId ->
                                navigationManager.navigate(object : NavigationCommand {
                                    override val destination: String =
                                        NavigationDestination.ImageDetails.route + imageId
                                })
                            })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreenSearchBar(modifier: Modifier = Modifier, onSearchBarClicked: () -> Unit) {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        enabled = false,
        modifier = modifier
            .clickable { onSearchBarClicked() }
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
        },
    ) {
    }
}

@Composable
fun CircleRevealItem(
    modifier: Modifier = Modifier,
    unsplashImage: UnsplashImage,
    onClick: (String) -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .clickable {
                onClick(unsplashImage.id)
            }) {
        AnimatedAsyncImage(
            url = unsplashImage.urls.imageUrl,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(.8f)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = .7f),
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "by ${unsplashImage.author.authorName}",
                style = androidx.compose.material.MaterialTheme.typography.h1.copy(
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.White)
            )
            if (unsplashImage.description != null) {
                Text(
                    text = unsplashImage.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        //fontSize = 14.sp,
                        //lineHeight = 22.sp,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}
