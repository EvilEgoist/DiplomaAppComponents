package testFeature.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.runtime.*
import uicomponents.moviePager.MoviePager

@Composable
fun MoviePagerScreen(){
    val visibility by remember {
        mutableStateOf(MutableTransitionState(false))
    }

    LaunchedEffect(key1 = Unit, block = {
        visibility.targetState = true
    })

    AnimatedVisibility(
        visibleState = visibility,
        enter = expandHorizontally(clip = false) + scaleIn(),
        exit = shrinkHorizontally()
    ) {
        MoviePager()
    }
}