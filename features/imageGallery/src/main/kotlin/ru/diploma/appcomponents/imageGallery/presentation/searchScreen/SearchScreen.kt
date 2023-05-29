package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility


import androidx.compose.animation.core.MutableTransitionState

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.imageGallery.domain.model.SearchHistoryModel
import ru.diploma.appcomponents.imageGallery.presentation.composable.ListContent
import ru.diploma.appcomponents.imageGallery.presentation.composable.SortOrderMenu
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.swipe.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchRoute(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {
    val searchSuggestions by viewModel.searchSuggestions.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()
    var active by rememberSaveable { mutableStateOf(false) }
    var focusRequester = remember { FocusRequester() }

    val sortOrderState = viewModel.sortOrderFlow.collectAsStateWithLifecycle()
    val currentSortOrder by remember{sortOrderState}

    LaunchedEffect(key1 = Unit, block = {
        if (searchQuery.isNotBlank()){
            viewModel.searchImages(searchQuery)
        }
        else focusRequester.requestFocus()
    })
    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedVisibility(
            visible = true,
            enter = expandVertically(clip = false),
            exit = shrinkHorizontally()
        ) {
            Column(Modifier.fillMaxSize()) {
                SearchWidget(
                    modifier = Modifier.focusRequester(focusRequester),
                    text = searchQuery,
                    onTextChange = {
                        viewModel.updateSearchQuery(query = it)
                    },
                    onSearchClicked = {
                        active = false
                        viewModel.searchImages(query = it)
                    },
                    onCloseClicked = {
                        navigationManager.navigate(object : NavigationCommand {
                            override val destination: String =
                                NavigationDestination.ImageGallery.route
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
                Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
                SortOrderMenu(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = MaterialTheme.spacing.small),
                    currentSortOrder = currentSortOrder,
                    onItemClick = {
                        viewModel.changeSortOrder(it)
                    })
                Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
                ListContent(items = searchedImages) {
                    navigationManager.navigate(object : NavigationCommand {
                        override val destination: String =
                            NavigationDestination.ImageDetails.route + it

                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchHints(
    searchSuggestions: List<SearchHistoryModel>,
    viewModel: SearchScreenViewModel,
    onSuggestionClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        itemsIndexed(
            items = searchSuggestions,
            key = { index: Int, _ -> searchSuggestions[index].id }
        ) { index, item ->
            val visibility by remember {
                mutableStateOf(MutableTransitionState(false))
            }

            LaunchedEffect(key1 = Unit, block = {
                visibility.targetState = true
            })

            AnimatedVisibility(
                visibleState = visibility,
                enter = expandVertically(clip = false),
                exit = shrinkHorizontally()
            ) {
                SwipeActions(
                    endActionsConfig = SwipeActionsConfig(
                        threshold = 0.4f,
                        background = Color(0xffFF4444),
                        iconTint = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        icon = Icons.Default.Delete,
                        stayDismissed = true,
                        onDismiss = {
                            visibility.targetState = false
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
                        modifier = Modifier.animateItemPlacement(
                            tween(200)
                        ),
                        shape = RoundedCornerShape(
                            topStart = startCorners,
                            bottomStart = startCorners,
                            topEnd = endCorners,
                            bottomEnd = endCorners,
                        ),
                        elevation = elevation,
                    ) {
                        SearchSuggestionItem(text = item.query, modifier = Modifier.clickable {
                            onSuggestionClicked()
                            viewModel.searchImages(item.query)
                        })
                    }
                }
            }
        }
    }
}

