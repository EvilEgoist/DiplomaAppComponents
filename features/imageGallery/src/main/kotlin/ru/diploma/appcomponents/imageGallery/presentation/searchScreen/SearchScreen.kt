package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import ru.diploma.appcomponents.imageGallery.presentation.mainScreen.mainScreenSearchBar

@Composable
fun SearchRoute(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {

    Surface(
        Modifier.fillMaxSize()
    ) {
        Text("Hey there screen 2")
    }
}

