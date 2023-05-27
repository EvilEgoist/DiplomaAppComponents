package ru.diploma.appcomponents.imageGallery.presentation.composable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ru.diploma.appcomponents.core.theme.spacing
import ru.diploma.appcomponents.imageGallery.util.SortOrder

@Composable
fun SortOrderMenu(
    currentSortOrder: SortOrder,
    modifier: Modifier = Modifier,
    onItemClick: (SortOrder) -> Unit
) {
    var isContextMenuVisible by remember {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
        ) {
            Row(
                Modifier.height(MaterialTheme.spacing.large),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconModifier = if (currentSortOrder == SortOrder.OLDEST) Modifier.scale(1f, -1f) else Modifier
                Icon(imageVector = Icons.Rounded.Sort, contentDescription = "sort order", modifier = iconModifier)
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(text = currentSortOrder.mapValueToUiText())
            }
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            enumValues<SortOrder>().forEach {
                DropdownMenuItem(
                    onClick = {
                        isContextMenuVisible = false
                        onItemClick(it)
                    }) {
                    Text(it.mapValueToUiText())
                }
            }
        }
    }
}

fun SortOrder.mapValueToUiText(): String {
    return when (this) {
        SortOrder.LATEST -> "Latest"
        SortOrder.OLDEST -> "Oldest"
        SortOrder.POPULAR -> "Popular"
    }
}