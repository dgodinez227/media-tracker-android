package edu.metrostate.ics342.mediatracker.ui.priorities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.FinishedColor
import edu.metrostate.ics342.mediatracker.theme.FinishedContainer
import edu.metrostate.ics342.mediatracker.theme.ProgressColor
import edu.metrostate.ics342.mediatracker.theme.ProgressContainer
import edu.metrostate.ics342.mediatracker.theme.WantColor
import edu.metrostate.ics342.mediatracker.theme.WantContainer
import kotlin.collections.emptyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesScreen(
    onBackClick: () -> Unit
) {
    PrioritiesContent(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrioritiesContent(
    onBackClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(edu.metrostate.ics342.mediatracker.R.string.priority_title),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "all" to edu.metrostate.ics342.mediatracker.R.string.priority_filter_all,
                    "high" to edu.metrostate.ics342.mediatracker.R.string.priority_filter_high,
                    "medium" to edu.metrostate.ics342.mediatracker.R.string.priority_filter_medium,
                    "low" to edu.metrostate.ics342.mediatracker.R.string.priority_filter_low
                )
                    .forEach { (key, labelRes) ->
                        FilterChip(
                            selected = selectedFilter == key,
                            onClick = { selectedFilter = key },
                            shape = RoundedCornerShape(8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            label = {
                                Text(stringResource(labelRes))
                            }
                        )
                    }
            }
            Text(
                text = stringResource(
                    edu.metrostate.ics342.mediatracker.R.string.priority_drag_to_reorder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

//copied from libraryItemCard, changed to fit priorities, later add viewmodel files
@Composable
private fun PriorityItemCard(
    item: Priority,
    onClick: () -> Unit,
    onEdit: () -> Unit,
  //  onRemove: () -> Unit //
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val (containerColor, labelColor) = when (item.priority) {
        1 -> WantContainer to WantColor
        2 -> ProgressContainer to ProgressColor
        3 -> FinishedContainer to FinishedColor
        else -> MaterialTheme.colorScheme.surfaceVariant to
                MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp, 90.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.media.coverUrl != null) {
                    AsyncImage(
                        model = item.media.coverUrl,
                        contentDescription = item.media.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (item.media.mediaType) {
                                    "book" -> "book"
                                    "movie" -> "movie"
                                    "show" -> "show"
                                    else -> "?"
                                },
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            // media column, and drag and drop box //
        }
    }
}


    @Preview(
        showBackground = true,
        showSystemUi = true
    )
    @Composable
    private fun PrioritiesPreview() {
        PrioritiesScreen(
            onBackClick = {}
        )
    }
