package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.presentation.mainScreen.ImageGalleryRoute
import javax.inject.Inject

class SearchScreenNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navigationManager: NavigationManager) {
        builder.composable(NavigationDestination.SearchImages.route) {
            SearchRoute(navigationManager = navigationManager)
        }
    }
}