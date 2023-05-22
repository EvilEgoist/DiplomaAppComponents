package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchRoute(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    val searchQuery by viewModel.searchQuery
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()
    var active by rememberSaveable { mutableStateOf(false) }

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize()) {
            SearchWidget(
                text = searchQuery,
                onTextChange = {
                    viewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    active = false
                    viewModel.searchImages(query = it)
                },
                onCloseClicked = { /*TODO*/ },
                active = active,
                onActiveChange = { active = it },
                onClearText = { /*TODO*/ },
                searchedContent = {SearchHints()})
            ListContent(items = searchedImages)
        }
    }
}

@Composable
fun SearchHints(){
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
        Text("Hint 1")
        Text("Hint 2")
        Text("Hint 3")
    }
}

