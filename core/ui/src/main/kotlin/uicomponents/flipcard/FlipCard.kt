package uicomponents.flipcard

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import ru.diploma.appcomponents.core.theme.dimensions

enum class CardState(val angle: Float) {
    FRONT(0f),
    BACK(180f)
}

fun CardState.parseNextSide(): CardState {
    return if (this == CardState.FRONT) CardState.BACK else CardState.FRONT
}

@Composable
fun AnimatedFlipCard() {
    FlipCard(
        modifier = Modifier.fillMaxSize(0.6f),
        front = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
        },
        back = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ){
                Text("Back")
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlipCard(
    onClick: (CardState) -> Unit = {},
    modifier: Modifier = Modifier,
    front: @Composable () -> Unit = {},
    back: @Composable () -> Unit = {}
) {
    var cardState by remember { mutableStateOf(CardState.FRONT) }
    val rotation by animateFloatAsState(
        targetValue = cardState.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        )
    )
    Card(
        onClick = {
            cardState = cardState.parseNextSide()
            onClick(cardState)
        },
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 10f * density
            },
        elevation = MaterialTheme.dimensions.CARD_ELEVATION,
        shape = RoundedCornerShape(MaterialTheme.dimensions.CARD_CORNER_SHAPE)
    ) {
        if (rotation <= 90f) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier.fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
            ) {
                back()
            }
        }
    }
}