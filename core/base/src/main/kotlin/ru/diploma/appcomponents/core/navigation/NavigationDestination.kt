    package ru.diploma.appcomponents.core.navigation

const val IMAGE_ID_PARAM_NAME = "image_id"
sealed class NavigationDestination(
    val route: String
) {
    object ImageGallery : NavigationDestination("imageGallery")

    object SearchImages : NavigationDestination("searchImages")

    object ImageDetails : NavigationDestination("imageDetails/")

    object MovieScreen : NavigationDestination("movieScreen")

    object Back : NavigationDestination("navigationBack")
}