package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import ru.diploma.appcomponents.imageGallery.presentation.composable.SortOrderMenu
import uicomponents.cicrleRevealPager.CircleRevealPager


@Composable
fun ImageGalleryRoute(
    viewModel: ImageGalleryViewModel = hiltViewModel(),
    navigationManager: NavigationManager,
) {

    var imageItems = viewModel.images.collectAsLazyPagingItems()

    val sortOrderState = viewModel.sortOrderFlow.collectAsStateWithLifecycle()
    val currentSortOrder by remember{sortOrderState}

    val visibility by remember {
        mutableStateOf(MutableTransitionState(false))
    }

    LaunchedEffect(key1 = Unit, block = {
        visibility.targetState = true
    })

    AnimatedVisibility(
        visibleState = visibility,
        enter = expandHorizontally (clip = false) + scaleIn(),
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
                ListContent(items = imageItems) {
                    navigationManager.navigate(object : NavigationCommand {
                        override val destination: String =
                            NavigationDestination.ImageDetails.route + it
                    })
                }
            }
        }
    }
    //CircleRevealPager()
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
