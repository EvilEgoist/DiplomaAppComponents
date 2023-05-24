package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onClearText: () -> Unit,
    searchedContent: @Composable () -> Unit
) {
    SearchBar(
        modifier = modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { onTextChange(it) },
        onSearch = {
            onSearchClicked(it)
        },
        active = active,
        onActiveChange = { onActiveChange(it) },
        placeholder = { Text(text = "Search here...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            onClearText()
                        } else {
                            onCloseClicked()
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "CloseIcon"
                )
            }
        }
    ) {
        searchedContent()
    }
}