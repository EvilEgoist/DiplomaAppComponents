package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.diploma.appcomponents.core.navigation.IMAGE_ID_PARAM_NAME
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.imageGallery.presentation.searchScreen.SearchRoute
import javax.inject.Inject

class DetailsScreenNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navigationManager: NavigationManager) {
        builder.composable(NavigationDestination.ImageDetails.route + "{$IMAGE_ID_PARAM_NAME}", arguments = listOf(
            navArgument(IMAGE_ID_PARAM_NAME) {
                type = NavType.StringType
            }
        )) {
            val id = it.arguments?.getString(IMAGE_ID_PARAM_NAME) ?: ""
            DetailsScreen(imageId = id, navigationManager = navigationManager)
        }
    }
}