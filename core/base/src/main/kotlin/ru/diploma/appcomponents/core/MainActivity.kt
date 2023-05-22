package ru.diploma.appcomponents.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.diploma.appcomponents.core.expandablecard.CardScreen
import ru.diploma.appcomponents.core.expandablecard.CardsScreenViewModel
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationFactory
import ru.diploma.appcomponents.core.navigation.NavigationHost
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.screens.AnimatedFlipCard
import ru.diploma.appcomponents.core.theme.*
import ru.diploma.appcomponents.core.utils.collectWithLifecycle
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val cardsViewModel by viewModels<CardsScreenViewModel>()

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    //TransparentSystemBars()
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
                        //CardScreen(viewModel = cardsViewModel)
                        NavigationHost(
                            navController = navController,
                            factories = navigationFactories,
                            navigationManager = navigationManager
                        )
                    }


                    navigationManager
                        .navigationEvent
                        .collectWithLifecycle(key = navController){
                            when (it.destination){
                                NavigationDestination.Back.route -> navController.navigateUp()
                                else -> navController.navigate(it.destination, it.configuration)
                            }
                        }
                }
            }
        }
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
                AnimatedFlipCard()
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
