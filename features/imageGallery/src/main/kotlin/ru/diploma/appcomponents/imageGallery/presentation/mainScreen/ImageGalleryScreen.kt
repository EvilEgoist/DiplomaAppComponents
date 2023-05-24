package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent


@Composable
fun ImageGalleryRoute(
    viewModel: ImageGalleryViewModel = hiltViewModel(),
    navigationManager: NavigationManager,
) {

    val imageItems = viewModel.getImages.collectAsLazyPagingItems()
    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize()) {
            mainScreenSearchBar {
                navigationManager.navigate(object: NavigationCommand{
                    override val destination: String = NavigationDestination.SearchImages.route
                })
            }
            ListContent(items = imageItems)
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
        modifier = Modifier.clickable { onSearchBarClicked() }
            .fillMaxWidth(),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
        },
    ) {
    }
}
