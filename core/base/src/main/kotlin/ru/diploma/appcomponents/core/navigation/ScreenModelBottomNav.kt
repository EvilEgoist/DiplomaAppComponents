package ru.diploma.appcomponents.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenModelBottomNav(
    val destination: NavigationDestination,
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    object ImageGallery: ScreenModelBottomNav(NavigationDestination.ImageGallery, "Gallery", Icons.Filled.Collections, Icons.Outlined.Collections)
    object MovieScreen: ScreenModelBottomNav(NavigationDestination.MovieScreen, "Movies", Icons.Filled.Movie, Icons.Outlined.Movie)
    //object Settings: ScreenModelBottomNav("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
}