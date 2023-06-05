package ru.diploma.appcomponents.core.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.diploma.appcomponents.core.theme.*

@Composable
fun NavigationDrawerContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        DrawerHeader()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.x_large))
        //AppsGrid()
    }
}

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "Hi, looks something?",
            style = MaterialTheme.typography.mediumDarkHeader
        )
        Text(
            text = "Apps menu",
            style = MaterialTheme.typography.bigDarkHeader
        )

    }
}

@Composable
fun AppsGrid() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = MaterialTheme.dimensions.APPS_SHORTCUTS_SIZE),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        content = {

        })
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFf1f3f3,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun DrawerPreview() {
    AppTheme() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            NavigationDrawerContent()
        }
    }
}