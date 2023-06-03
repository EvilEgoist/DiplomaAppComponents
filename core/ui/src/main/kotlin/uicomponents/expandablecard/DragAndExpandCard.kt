package uicomponents.expandablecard

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.diploma.appcomponents.core.theme.*
import kotlin.math.roundToInt

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DragAndExpandCard(
    cardModel: ExpandableCardModel,
    cardHeight: Dp,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    var expandedState by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = transitionState, "cardTransition")
    val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
        }
    )
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset - offsetX else -offsetX }
    )
    val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) REVEALED_CARD_ELEVATION else REGULAR_CARD_ELEVATION }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .height(cardHeight)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION,
                    easing = LinearOutSlowInEasing
                )
            )
            .offset { IntOffset((offsetX + offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        return@detectHorizontalDragGestures
                    }
                    if (change.positionChange() != Offset.Zero) change.consume()
                    offsetX = newValue.x
                }
            },
        backgroundColor = cardBgColor,
        shape = remember {
            RoundedCornerShape(0.dp)
        },
        elevation = cardElevation
    ) {
        ExpandableCardContent(cardModel = cardModel, expandedState = expandedState) {
            expandedState = !expandedState
        }
    }
}

@Composable
fun ExpandableCardContent(
    cardModel: ExpandableCardModel,
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    onExpand: () -> Unit
) {
    val iconRotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(6f),
                text = cardModel.title,
                style = MaterialTheme.typography.darkRegularHeader,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CustomMultilineTextfield(
    text: String,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    hint: String = "",
    mainTextStyle: TextStyle = MaterialTheme.typography.darkRegularText,
    hintTextStyle: TextStyle = MaterialTheme.typography.hintTextStyle,
    maxLines: Int = 15,
) {
    BasicTextField(
        value = text,
        onValueChange = onValueChanged,
        textStyle = mainTextStyle,
        maxLines = maxLines,
        decorationBox = { innerTextField ->
            Box(modifier = modifier) {
                if (text.isEmpty()) {
                    Text(text = hint, style = hintTextStyle)
                }
                innerTextField
            }
        }
    )
}

@Composable
fun CardScreen(viewModel: CardsScreenViewModel) {

    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()

    LazyColumn() {
        items(cards, ExpandableCardModel::id) { card ->
            Box(Modifier.fillMaxWidth()) {
                ActionsRow(
                    actionIconSize = MaterialTheme.dimensions.EXPANDABLE_CARD_HEIGHT,
                    onDelete = {},
                    onEdit = {},
                    onFavorite = {}
                )
                //for advanced cases use DraggableCardComplex
                DragAndExpandCard(
                    cardModel = card,
                    isRevealed = revealedCardIds.contains(card.id),
                    cardHeight = MaterialTheme.dimensions.EXPANDABLE_CARD_HEIGHT,
                    cardOffset = calculateCardOffset(MaterialTheme.dimensions.EXPANDABLE_CARD_HEIGHT),
                    onExpand = { viewModel.onItemExpanded(card.id) },
                    onCollapse = { viewModel.onItemCollapsed(card.id) },
                )
            }
        }
    }
}
