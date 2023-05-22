package ru.diploma.appcomponents.core.navigation

sealed class NavigationDestination(
    val route: String
) {
    object ImageGallery : NavigationDestination("imageGallery")

    object SearchImages : NavigationDestination("searchImages")

    object Back : NavigationDestination("navigationBack")
}