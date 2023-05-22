package ru.diploma.appcomponents.core.expandablecard

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val ANIMATION_DURATION = 500
const val MIN_DRAG_AMOUNT = 6
val REVEALED_CARD_ELEVATION = 25.dp
val REGULAR_CARD_ELEVATION = 4.dp
const val ICONS_NUMBER_IN_ACTIONROW = 3

fun Float.dp(): Float = this * density + 0.5f

val density: Float
    get() = Resources.getSystem().displayMetrics.density

fun calculateCardOffset(cardHeight: Dp): Float{
    return cardHeight.value.dp() * ICONS_NUMBER_IN_ACTIONROW
}