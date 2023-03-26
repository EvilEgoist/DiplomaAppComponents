package ru.diploma.appcomponents.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val SMALL_ICON: Dp = 16.dp,
    val MEDIUM_ICON: Dp = 25.dp,
    val LOGO_SIZE: Dp = 60.dp,
    val LOGO_CORNER_SHAPE: Dp = 10.dp,
    val CARD_WIDTH: Dp = 300.dp,
)

val LocalDimensions = compositionLocalOf { Dimensions() }

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current