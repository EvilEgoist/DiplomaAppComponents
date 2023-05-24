package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.swipe.SwipeActions
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.swipe.SwipeActionsConfig
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.swipe.animatedItemsIndexed
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.swipe.updateAnimatedItemsState
import timber.log.Timber
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchRoute(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    val searchQuery by viewModel.searchQuery
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()
    var active by rememberSaveable { mutableStateOf(false) }
    var focusRequester = remember { FocusRequester() }
    val searchSuggestions by viewModel.searchSuggestions.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        focusRequester.requestFocus()
    })
    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize()) {
            SearchWidget(
                modifier = Modifier.focusRequester(focusRequester),
                text = searchQuery,
                onTextChange = {
                    viewModel.updateSearchQuery(query = it)
                    viewModel.getSearchSuggestions()
                },
                onSearchClicked = {
                    active = false
                    viewModel.searchImages(query = it)
                },
                onCloseClicked = {
                    navigationManager.navigate(object : NavigationCommand {
                        override val destination: String = NavigationDestination.ImageGallery.route
                    })
                },
                active = active,
                onActiveChange = { active = it },
                onClearText = {
                    viewModel.updateSearchQuery("")
                }
            ) {
                SearchHints(searchSuggestions, viewModel) {
                    active = false
                }
            }
            ListContent(items = searchedImages)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchHints(searchSuggestions: List<SearchHistoryModel>, viewModel: SearchScreenViewModel, onHintClicked: () -> Unit) {

    val animatedList by updateAnimatedItemsState(newList = searchSuggestions)

    Log.d("MY_TAG", "SearchHints: searchSugg size: ${searchSuggestions.size} and animated list size ${animatedList.size}")
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        animatedItemsIndexed(
            state = animatedList,
            key = {
                it.id
            }
        ) { index, item ->
            Log.d("MY_TAG", "SearchHints: index: $index")
            SwipeActions(
                endActionsConfig = SwipeActionsConfig(
                    threshold = 0.4f,
                    background = Color(0xffFF4444),
                    iconTint = Color.Black,
                    icon = Icons.Default.Delete,
                    stayDismissed = true,
                    onDismiss = {
                        viewModel.deleteSuggestion(item.id)
                    }
                ),
                showTutorial = index == 0
            ) { state ->
                val animateCorners by remember {
                    derivedStateOf {
                        state.offset.value.absoluteValue > 30
                    }
                }
                val startCorners by animateDpAsState(
                    targetValue = when {
                        state.dismissDirection == DismissDirection.StartToEnd &&
                                animateCorners -> 8.dp
                        else -> 0.dp
                    }
                )
                val endCorners by animateDpAsState(
                    targetValue = when {
                        state.dismissDirection == DismissDirection.EndToStart &&
                                animateCorners -> 8.dp
                        else -> 0.dp
                    }
                )
                val elevation by animateDpAsState(
                    targetValue = when {
                        animateCorners -> 6.dp
                        else -> 2.dp
                    }
                )
                Card(
                    shape = RoundedCornerShape(
                        topStart = startCorners,
                        bottomStart = startCorners,
                        topEnd = endCorners,
                        bottomEnd = endCorners,
                    ),
                    elevation = elevation,
                ) {
                    SearchSuggestionItem(text = item.query, modifier = Modifier.clickable {
                        onHintClicked()
                        viewModel.searchImages(item.query)
                    })
                }
            }
        }
    }
}

