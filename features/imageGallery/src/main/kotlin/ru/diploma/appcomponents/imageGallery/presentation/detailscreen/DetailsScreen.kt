package ru.diploma.appcomponents.imageGallery.presentation.detailscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.diploma.appcomponents.core.navigation.NavigationCommand
import ru.diploma.appcomponents.core.navigation.NavigationDestination
import ru.diploma.appcomponents.core.navigation.NavigationManager
import ru.diploma.appcomponents.core.theme.dimensions
import ru.diploma.appcomponents.core.theme.headerWithoutColor
import ru.diploma.appcomponents.core.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    imageId: String,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    navigationManager: NavigationManager
) {

    val uiState by viewModel.detailsScreenUiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getImageDetails(imageId)
    })

    val containerColor = if (uiState.image != null) Color(uiState.image!!.color.toColorInt())
    else MaterialTheme.colorScheme.background

    Scaffold(containerColor = containerColor,
        topBar = {
            TopAppBar(title = { }, modifier = Modifier.height(MaterialTheme.spacing.x_large), navigationIcon = {
                IconButton(onClick = {
                    navigationManager.navigate(object : NavigationCommand {
                        override val destination: String = NavigationDestination.Back.route
                    })
                }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                }
            })
        }) {
        DetailsScreenContent(it, uiState)
    }
}

@Composable
private fun DetailsScreenContent(
    it: PaddingValues,
    uiState: DetailsScreenUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.PROGRESS_INDICATOR_SIZE)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else if (uiState.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = uiState.error,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.headerWithoutColor
                )
            }
        } else {
            val image = uiState.image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image?.urls?.imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .clip(RoundedCornerShape(MaterialTheme.dimensions.LOGO_CORNER_SHAPE)),
                contentDescription = "Unsplash Image",
                //contentScale = ContentScale.Crop
            )
        }
    }
}


