package ru.diploma.appcomponents.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>,
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.ImageGallery.route,
        modifier = modifier
    ){
        factories.forEach {
            it.create(this, navigationManager)
        }
    }
}