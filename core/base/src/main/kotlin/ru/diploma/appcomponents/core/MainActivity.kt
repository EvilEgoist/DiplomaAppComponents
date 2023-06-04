package ru.diploma.appcomponents.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationHost
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.theme.AppTheme
import ru.diploma.appcomponents.core.utils.collectWithLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//                val scope = rememberCoroutineScope()
//                ModalDrawer(
//                    drawerContent = {
//                        NavigationDrawerContent()
//                    },
//                    drawerState = drawerState,
//                    content = {
//                        MainScreen(drawerState, scope)
//                    },
//                    drawerShape = RoundedCornerShape(
//                        topEnd = MaterialTheme.dimensions.DRAWER_CORNER_SHAPE,
//                        bottomEnd = MaterialTheme.dimensions.DRAWER_CORNER_SHAPE
//                    )
//                )
                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {
                        //TransparentSystemBars()
                        //CardScreen(viewModel = cardsViewModel)
                        NavigationHost(
                            navController = navController,
                            factories = navigationFactories,
                            navigationManager = navigationManager
                        )
                    }


                    navigationManager
                        .navigationEvent
                        .collectWithLifecycle(key = navController) {
                            when (it.destination) {
                                NavigationDestination.Back.route -> navController.navigateUp()
                                else -> navController.navigate(it.destination, it.configuration)
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFf1f3f3,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    AppTheme {
        var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                Modifier
                    .height(400.dp)
                    .width(400.dp),
                contentAlignment = Alignment.Center
            ) {
            }
        }
//        ModalDrawer(
//            drawerContent = {
//                NavigationDrawerContent()
//            },
//            drawerState = drawerState,
//            content = {
//                MainScreen(drawerState, scope)
//            },
//            drawerShape = RoundedCornerShape(
//                topEnd = MaterialTheme.dimensions.DRAWER_CORNER_SHAPE,
//                bottomEnd = MaterialTheme.dimensions.DRAWER_CORNER_SHAPE
//            )
//        )
    }
}

