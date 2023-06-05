package testFeature.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationManager
import javax.inject.Inject

class MovieScreenNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder, navigationManager: NavigationManager) {
        builder.composable(NavigationDestination.MovieScreen.route) {
            MoviePagerScreen()
        }
    }
}