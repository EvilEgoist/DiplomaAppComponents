package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


@Composable
fun ImageGalleryRoute(
    viewModel: ImageGalleryViewModel = hiltViewModel(),
    navigationManager: NavigationManager,
) {

    val imageItemsFlow by remember{ mutableStateOf(viewModel.getImages) }
    var imageItems = imageItemsFlow.collectAsLazyPagingItems()

    val sortOrderState = viewModel.sortOrderFlow.collectAsStateWithLifecycle()
    val currentSortOrder by remember{sortOrderState}

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize()) {
            mainScreenSearchBar {
                navigationManager.navigate(object : NavigationCommand {
                    override val destination: String = NavigationDestination.SearchImages.route
                })
            }
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            SortOrderMenu(modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(start = MaterialTheme.spacing.small), currentSortOrder = currentSortOrder, onItemClick = {
                viewModel.changeSortOrder(it)
                navigationManager.navigate(object : NavigationCommand{
                    override val destination: String = NavigationDestination.ImageGallery.route
                })
            } )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            ListContent(items = imageItems) {
                navigationManager.navigate(object : NavigationCommand {
                    override val destination: String = NavigationDestination.ImageDetails.route + it
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreenSearchBar(onSearchBarClicked: () -> Unit) {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        enabled = false,
        modifier = Modifier
            .clickable { onSearchBarClicked() }
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
        },
    ) {
    }
}
