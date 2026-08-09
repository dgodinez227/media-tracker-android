package edu.metrostate.ics342.mediatracker.ui.priorities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
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
import edu.metrostate.ics342.mediatracker.ui.library.LibraryViewModel
import edu.metrostate.ics342.mediatracker.ui.library.PriorityError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesScreen(
    onBackClick: () -> Unit,
    onMediaClick: (Int) -> Unit,
    viewModel: LibraryViewModel = viewModel()
) {
    val priorities by viewModel.priorities.collectAsState()
    val isLoading by viewModel.prioritiesLoading.collectAsState()

    val priorityError by viewModel.priorityError.collectAsState()

    PrioritiesContent(
        onBackClick = onBackClick,
        onMediaClick = onMediaClick,
        priorities = priorities,
        isLoading = isLoading,
        priorityError = priorityError,
        onReorder = { fromMediaId, toMediaId ->
            viewModel.reorderPriorities(
                fromMediaId = fromMediaId,
                toMediaId = toMediaId
            )
        },
        onUpdatePriority = { item, newPriority, estimatedHours, notes ->
            viewModel.updatePriority(
                mediaId = item.mediaId,
                priority = newPriority,
                orderIndex = item.orderIndex,
                estimatedTimeHours = estimatedHours,
                notes = notes
            )
        },
        onRetry = {
            viewModel.loadPriorities()
        },
        onClearError = {
            viewModel.clearPriorityError()
        }

        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrioritiesContent(
    onBackClick: () -> Unit,
    onMediaClick: (Int) -> Unit,
    priorities: List<Priority>,
    isLoading: Boolean,
    priorityError: PriorityError?,
    onReorder: (Int, Int) -> Unit,
    onUpdatePriority: (
        Priority,
        Int,
        Int,
        String
    ) -> Unit,
    onRetry: () -> Unit,
    onClearError: () -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf("all")
    }
    var draggedIndex by remember {
        mutableStateOf<Int?>(null)
    }

    var dragAmount by remember {
        mutableStateOf(0f)
    }
    if (
        priorityError != null &&
        priorityError != PriorityError.LOAD_FAILED
    ) {
        AlertDialog(
            onDismissRequest = onClearError,
            title = {
                Text("Priority Error")
            },
            text = {
                Text(
                    when (priorityError) {
                        PriorityError.MAX_REACHED ->
                            "Up to 5 items only"

                        PriorityError.UPDATE_FAILED ->
                            "The priority could not be updated, please try again."

                        else ->
                            "unknown error."
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onClearError
                ) {
                    Text("OK")
                }
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            edu.metrostate.ics342.mediatracker.R.string.priority_title
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                edu.metrostate.ics342.mediatracker.R.string.action_back
                            )
                        )
                    }
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

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                return@Column
            }

            if (priorityError == PriorityError.LOAD_FAILED) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {
                        Text(
                            text =
                                "Could not load priorities.",
                            style =
                                MaterialTheme.typography.bodyLarge
                        )

                        Spacer(
                            Modifier.height(12.dp)
                        )

                        Button(
                            onClick = onRetry
                        ) {
                            Text("Retry")
                        }
                    }
                }

                return@Column
            }

            if (priorities.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Text(
                        text =
                            "No priorities yet.\nAdd a Want To item from your Library.",
                        style =
                            MaterialTheme.typography.bodyLarge,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                return@Column
            }

            val filteredPriorities = priorities
                .sortedBy { it.orderIndex }
                .filter { item ->
                    when (selectedFilter) {
                        "high" -> item.priority == 1
                        "medium" -> item.priority == 2
                        "low" -> item.priority == 3
                        else -> true
                    }
                }

            if (filteredPriorities.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Text(
                        text =
                            "No items in this priority level.",
                        style =
                            MaterialTheme.typography.bodyLarge,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                return@Column
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 4.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = filteredPriorities,
                    key = { _, item -> item.mediaId }
                ) { index, item ->

                    PriorityItemCard(
                        item = item,
                        onClick = {
                            onMediaClick(item.media.id)
                        },
                        onSaveChanges = {
                                newPriority,
                                estimatedHours,
                                notes ->

                            onUpdatePriority(
                                item,
                                newPriority,
                                estimatedHours,
                                notes
                            )
                        },
                        dragModifier = Modifier.pointerInput(item.mediaId) {

                            detectDragGestures(
                                onDragStart = {
                                    draggedIndex = index
                                    dragAmount = 0f
                                },

                                onDragEnd = {
                                    draggedIndex = null
                                    dragAmount = 0f
                                },

                                onDragCancel = {
                                    draggedIndex = null
                                    dragAmount = 0f
                                },

                                onDrag = { change, dragDistance ->
                                    change.consume()

                                    dragAmount += dragDistance.y

                                    val from = draggedIndex ?: return@detectDragGestures
                                    val moveThreshold = 120f

                                    if (
                                        dragAmount > moveThreshold &&
                                        from < filteredPriorities.lastIndex
                                    ) {

                                        val fromItem =
                                            filteredPriorities[from]

                                        val toItem =
                                            filteredPriorities[from + 1]

                                        onReorder(
                                            fromItem.mediaId,
                                            toItem.mediaId
                                        )

                                        draggedIndex =
                                            from + 1

                                        dragAmount =
                                            0f
                                    }


                                    if (
                                        dragAmount <
                                        -moveThreshold &&
                                        from > 0
                                    ) {

                                        val fromItem =
                                            filteredPriorities[from]

                                        val toItem =
                                            filteredPriorities[from - 1]

                                        onReorder(
                                            fromItem.mediaId,
                                            toItem.mediaId
                                        )

                                        draggedIndex =
                                            from - 1

                                        dragAmount =
                                            0f
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

    //copied from libraryItemCard, changed to fit priorities, later add viewmodel files
    @Composable
    private fun PriorityItemCard(
        item: Priority,
        onClick: () -> Unit,
        onSaveChanges: (
            Int,
            Int,
            String
        ) -> Unit,
        dragModifier: Modifier = Modifier
    ) {

        var editDialogVisible by remember {
            mutableStateOf(false)
        }

        var selectedPriority by remember(item.mediaId) {
            mutableStateOf(
                item.priority
            )
        }

        var estimatedHoursText by remember(item.mediaId) {
            mutableStateOf(
                item.estimatedTimeHours.toString()
            )
        }

        var notesText by remember(item.mediaId) {
            mutableStateOf(
                item.notes
            )
        }

        val (containerColor, labelColor) = when (item.priority) {
            1 -> WantContainer to WantColor
            2 -> ProgressContainer to ProgressColor
            3 -> FinishedContainer to FinishedColor
            else -> MaterialTheme.colorScheme.surfaceVariant to
                    MaterialTheme.colorScheme.onSurfaceVariant
        }
        if (editDialogVisible) {

            val parsedHours =
                estimatedHoursText.toIntOrNull()

            AlertDialog(
                onDismissRequest = {
                    editDialogVisible = false
                },
                title = {
                    Text("Edit Priority")
                },
                text = {
                    Column {

                        Text(
                            text = "Priority",
                            style =
                                MaterialTheme.typography.labelLarge
                        )

                        Spacer(
                            Modifier.height(4.dp)
                        )


                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            FilterChip(
                                selected =
                                    selectedPriority == 1,
                                onClick = {
                                    selectedPriority = 1
                                },
                                label = {
                                    Text("High")
                                }
                            )

                            FilterChip(
                                selected =
                                    selectedPriority == 2,
                                onClick = {
                                    selectedPriority = 2
                                },
                                label = {
                                    Text("Medium")
                                }
                            )

                            FilterChip(
                                selected =
                                    selectedPriority == 3,
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
                            value =
                                estimatedHoursText,
                            onValueChange = { value ->

                                if (
                                    value.all {
                                        it.isDigit()
                                    }
                                ) {
                                    estimatedHoursText =
                                        value
                                }
                            },
                            label = {
                                Text(
                                    "Estimated Hours"
                                )
                            },
                            keyboardOptions =
                                KeyboardOptions(
                                    keyboardType =
                                        KeyboardType.Number
                                ),
                            singleLine = true,
                            modifier =
                                Modifier.fillMaxWidth()
                        )


                        Spacer(
                            Modifier.height(12.dp)
                        )


                        OutlinedTextField(
                            value =
                                notesText,
                            onValueChange = { value ->

                                // API maximum is 200 characters.
                                if (
                                    value.length <= 200
                                ) {
                                    notesText =
                                        value
                                }
                            },
                            label = {
                                Text("Notes")
                            },
                            supportingText = {
                                Text(
                                    "${notesText.length}/200"
                                )
                            },
                            modifier =
                                Modifier.fillMaxWidth()
                        )
                    }
                },

                confirmButton = {
                    TextButton(
                        enabled =
                            parsedHours != null &&
                                    parsedHours >= 0,
                        onClick = {
                            if (
                                parsedHours != null
                            ) {
                                onSaveChanges(
                                    selectedPriority,
                                    parsedHours,
                                    notesText
                                )
                                editDialogVisible =
                                    false
                            }
                        }
                    ) {
                        Text("Save")
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = {
                            editDialogVisible =
                                false
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
                Icon(
                    imageVector = Icons.Default.DragIndicator,
                    contentDescription = "Drag to reorder",
                    modifier = dragModifier
                        .size(32.dp)
                        .padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

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

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.media.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = item.media.creatorCredit(LocalContext.current),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(6.dp))

                    SuggestionChip(
                        onClick = {

                            selectedPriority =
                                item.priority

                            estimatedHoursText =
                                item.estimatedTimeHours
                                    .toString()

                            notesText =
                                item.notes

                            editDialogVisible =
                                true
                        },
                        colors =
                            SuggestionChipDefaults
                                .suggestionChipColors(
                                    containerColor =
                                        containerColor
                                ),
                        label = {

                            Text(
                                text =
                                    when (
                                        item.priority
                                    ) {
                                        1 ->
                                            "High Priority"

                                        2 ->
                                            "Medium Priority"

                                        3 ->
                                            "Low Priority"

                                        else ->
                                            "Priority"
                                    },
                                style =
                                    MaterialTheme.typography.labelSmall,
                                color =
                                    labelColor
                            )
                        }
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Est. ${item.estimatedTimeHours} hours",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (item.notes.isNotBlank()) {
                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = item.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }