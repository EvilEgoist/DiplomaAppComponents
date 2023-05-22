package ru.diploma.appcomponents.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavigationFactory {
    fun create(builder: NavGraphBuilder, navigationManager: NavigationManager)
}