package ru.diploma.appcomponents.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val SMALL_ICON: Dp = 16.dp,
    val MEDIUM_ICON: Dp = 25.dp,
    val DEFAULT_BIG_ICON: Dp = 35.dp,
    val CUSTOM_ICON_BIG: Dp = 30.dp,
    val LOGO_SIZE: Dp = 60.dp,
    val APPS_SHORTCUTS_SIZE: Dp = 60.dp,

    val LOGO_CORNER_SHAPE: Dp = 10.dp,
    val INPUT_TEXTFIELD_CORNER_SHAPE: Dp = 10.dp,
    val CARD_CORNER_SHAPE: Dp = 20.dp,
    val DRAWER_CORNER_SHAPE: Dp = 15.dp,
    val CARD_WIDTH: Dp = 300.dp,

    val EXPANDABLE_CARD_HEIGHT: Dp = 50.dp,

    //Elevations
    val CARD_ELEVATION: Dp = 6.dp,

    val SEARCH_BAR_HEIGHT: Dp = 56.dp,
    val SEARCH_BAR_CORNER_PERCENT: Int = 50,

    //Details Screen
    val PROGRESS_INDICATOR_SIZE: Dp = 40.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current