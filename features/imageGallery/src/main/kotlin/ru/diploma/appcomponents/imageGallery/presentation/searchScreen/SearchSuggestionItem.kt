package ru.diploma.appcomponents.imageGallery.presentation.searchScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.diploma.appcomponents.core.theme.dimensions
import ru.diploma.appcomponents.core.theme.regularTextWithoutColor
import ru.diploma.appcomponents.core.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSuggestionItem(
    text: String,
    modifier: Modifier = Modifier
) {

    Column {
        Surface(color = SearchBarDefaults.colors().containerColor) {
            Row(
                modifier = modifier.fillMaxWidth()
                    .height(MaterialTheme.dimensions.SEARCH_BAR_HEIGHT),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "icon history",
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    text = text,
                    style = MaterialTheme.typography.regularTextWithoutColor,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Divider(
            thickness = 0.dp
        )
    }

}