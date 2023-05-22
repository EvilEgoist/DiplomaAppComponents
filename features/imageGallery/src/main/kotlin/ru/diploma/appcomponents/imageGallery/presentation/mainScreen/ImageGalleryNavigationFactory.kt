package ru.diploma.appcomponents.imageGallery.presentation.mainScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.diploma.appcomponents.core.navigation.NavigationDestination.ImageGallery
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationManager
import javax.inject.Inject

class ImageGalleryNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navigationManager: NavigationManager) {
        builder.composable(ImageGallery.route) {
            ImageGalleryRoute(navigationManager = navigationManager)
        }
    }
}