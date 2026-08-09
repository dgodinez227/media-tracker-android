package edu.metrostate.ics342.mediatracker.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.WantColor
import edu.metrostate.ics342.mediatracker.theme.WantContainer
import edu.metrostate.ics342.mediatracker.theme.ProgressColor
import edu.metrostate.ics342.mediatracker.theme.ProgressContainer
import edu.metrostate.ics342.mediatracker.theme.FinishedColor
import edu.metrostate.ics342.mediatracker.theme.FinishedContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onMediaClick: (Int) -> Unit,
    onPrioritiesClick: () -> Unit,
    viewModel: LibraryViewModel = viewModel()
) {
    val items     by viewModel.libraryItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val priorities by viewModel.priorities.collectAsState()
    val prioritiesFull = priorities.size >= MAX_PRIORITIES

    val selectedStatus by viewModel.filterState.collectAsState()
    var selectedType by rememberSaveable { mutableStateOf("all") }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(
                    text = stringResource(
                        edu.metrostate.ics342.mediatracker.R.string.library_title
                    )
                )
            },
            actions = {
                TextButton(
                    onClick = onPrioritiesClick
                ) {
                    Text(
                        text = stringResource(
                            edu.metrostate.ics342.mediatracker.R.string.priority_title
                        )
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll( state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "all"   to edu.metrostate.ics342.mediatracker.R.string.filter_all,
                "book"  to edu.metrostate.ics342.mediatracker.R.string.filter_books,
                "movie" to edu.metrostate.ics342.mediatracker.R.string.filter_movies,
                "show"  to edu.metrostate.ics342.mediatracker.R.string.filter_shows
            )
                .forEach { (key, labelRes) ->
                    FilterChip(
                        selected = selectedType == key,
                        onClick  = { selectedType = key },
                        // rounded corners to 8.dp and added theme colors
                        shape = RoundedCornerShape(8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        label    = { Text(stringResource(labelRes)) }
                    )
                }
        }

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            LibraryStatus.values().forEachIndexed { index, status ->
                SegmentedButton(
                    shape    = SegmentedButtonDefaults.itemShape(
                        index = index, count = LibraryStatus.values().size),
                    selected = selectedStatus == status,
                    onClick  = { viewModel.updateFilter(status) },
                    label    = { Text(stringResource(status.labelRes)) }
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        val filteredItems = items
            .filter { it.status == selectedStatus }
            .filter { selectedType == "all" || it.media.mediaType == selectedType }

        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(edu.metrostate.ics342.mediatracker.R.string.library_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            return@Column
        }

        Text(
            if (filteredItems.size == 1) stringResource(edu.metrostate.ics342.mediatracker.R.string.library_item_count, filteredItems.size)
            else stringResource(edu.metrostate.ics342.mediatracker.R.string.library_items_count, filteredItems.size),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style    = MaterialTheme.typography.labelMedium,
            color    = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredItems, key = { it.mediaId }) { item ->
                val alreadyPrioritized =
                    priorities.any {
                        it.mediaId == item.mediaId
                    }
                LibraryItemCard(
                    item           = item,
                    onClick = { onMediaClick(item.media.id) },
                    onRemove       = { viewModel.removeItem(item.mediaId) },
                    onStatusChange = { newStatus -> viewModel.updateStatus(item.mediaId, newStatus) },
                    onSetPriority = { priority, estimatedHours, notes ->
                        viewModel.addPriority(
                            mediaId = item.mediaId,
                            priority = priority,
                            estimatedTimeHours = estimatedHours,
                            notes = notes
                        )
                    },
                    canSetPriority =
                        !prioritiesFull || alreadyPrioritized
                )
            }
        }
    }
}
@Composable
private fun LibraryItemCard(
    item: LibraryItem,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    onSetPriority: (Int, Int, String) -> Unit,
    onStatusChange: (LibraryStatus) -> Unit,
    canSetPriority: Boolean
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var statusDialogVisible by remember { mutableStateOf(false) }
    var priorityDialogVisible by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(1) }
    var estimatedHoursText by remember { mutableStateOf("") }
    var priorityNotes by remember { mutableStateOf("") }

    val (containerColor, labelColor) = when (item.status) {
        LibraryStatus.WANT_TO -> WantContainer to WantColor
        LibraryStatus.IN_PROGRESS -> ProgressContainer to ProgressColor
        LibraryStatus.FINISHED -> FinishedContainer to FinishedColor
    }

    if (statusDialogVisible) {
        AlertDialog(
            onDismissRequest = { statusDialogVisible = false },
            title = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.action_change_status)) },
            text = {
                Column {
                    LibraryStatus.values().forEach { s ->
                        TextButton(
                            onClick  = { onStatusChange(s); statusDialogVisible = false },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(stringResource(s.labelRes)) }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { statusDialogVisible = false }) { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.settings_cancel_button)) }
            }
        )
    }
    if (priorityDialogVisible) {

        val estimatedHours =
            estimatedHoursText.toIntOrNull()

        AlertDialog(
            onDismissRequest = {
                priorityDialogVisible = false
            },

            title = {
                Text("Set Priority")
            },

            text = {
                Column {

                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(
                        Modifier.height(4.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        FilterChip(
                            selected = selectedPriority == 1,
                            onClick = {
                                selectedPriority = 1
                            },
                            label = {
                                Text("High")
                            }
                        )

                        FilterChip(
                            selected = selectedPriority == 2,
                            onClick = {
                                selectedPriority = 2
                            },
                            label = {
                                Text("Medium")
                            }
                        )

                        FilterChip(
                            selected = selectedPriority == 3,
                            onClick = {
                                selectedPriority = 3
                            },
                            label = {
                                Text("Low")
                            }
                        )
                    }

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = estimatedHoursText,
                        onValueChange = { value ->
                            if (value.all { it.isDigit() }) {
                                estimatedHoursText = value
                            }
                        },
                        label = {
                            Text("Estimated Hours")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = priorityNotes,
                        onValueChange = { value ->
                            if (value.length <= 200) {
                                priorityNotes = value
                            }
                        },
                        label = {
                            Text("Notes (optional)")
                        },
                        supportingText = {
                            Text("${priorityNotes.length}/200")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            confirmButton = {
                TextButton(
                    enabled =
                        estimatedHours != null &&
                                estimatedHours >= 0,

                    onClick = {
                        if (estimatedHours != null) {
                            onSetPriority(
                                selectedPriority,
                                estimatedHours,
                                priorityNotes
                            )

                            priorityDialogVisible = false

                            // Reset for next item
                            selectedPriority = 1
                            estimatedHoursText = ""
                            priorityNotes = ""
                        }
                    }
                ) {
                    Text("Save")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        priorityDialogVisible = false
                    }
                ) {
                    Text(
                        stringResource(
                            edu.metrostate.ics342.mediatracker.R.string.settings_cancel_button
                        )
                    )
                }
            }
        )
    }

    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        // card elevation to 2.dp
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp, 90.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.media.coverUrl != null) {
                    AsyncImage(
                        model             = item.media.coverUrl,
                        contentDescription = item.media.title,
                        contentScale      = ContentScale.Crop,
                        modifier          = Modifier.fillMaxSize()
                    )
                } else {
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxSize()) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(when (item.media.mediaType) {
                                "book" -> "📖"; "movie" -> "🎬"; "show" -> "📺"
                                else -> "?"
                            }, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.media.title, style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold, maxLines = 2)
                Spacer(Modifier.height(2.dp))
                Text(item.media.creatorCredit(LocalContext.current),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(6.dp))
                SuggestionChip(
                    onClick = { statusDialogVisible = true },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = containerColor
                    ),
                    label   = { Text(stringResource(item.status.labelRes),
                        style = MaterialTheme.typography.labelSmall, color = labelColor) }
                )
            }

            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Outlined.MoreVert, stringResource(edu.metrostate.ics342.mediatracker.R.string.action_more_options))
                }
                DropdownMenu(
                    expanded         = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text    = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.action_change_status)) },
                        onClick = { menuExpanded = false; statusDialogVisible = true }
                    )

                    if (
                        item.status ==
                        LibraryStatus.WANT_TO
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    if (canSetPriority) {
                                        "Set Priority"
                                    } else {
                                        "Priorities Full (5/5)"
                                    }
                                )
                            },
                            enabled =
                                canSetPriority,
                            onClick = {
                                menuExpanded = false
                                priorityDialogVisible =
                                    true
                            }
                        )
                    }
                    DropdownMenuItem(
                        text    = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.action_remove_from_library),
                            color = MaterialTheme.colorScheme.error) },
                        onClick = { menuExpanded = false; onRemove() }
                    )
                }
            }
        }
    }
}
