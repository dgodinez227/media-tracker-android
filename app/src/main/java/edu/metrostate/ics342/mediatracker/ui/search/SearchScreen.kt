package edu.metrostate.ics342.mediatracker.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.R
// ── STUB — Students build this in Week 5 ─────────────────────────────────────
//
// Week 5 task: Build the Search screen.
//   1. Add a search bar (SearchBar or OutlinedTextField) at the top.
//   2. Add FilterChips for All / Books / Movies / Shows in a horizontally scrollable Row.
//   3. Display results in a LazyColumn (you'll learn why Column won't work here).
//   4. Wire to GET /media?query=...&type=...
//   5. Handle loading, empty, and error states.


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    onMediaClick: (Int) -> Unit,
    viewModel: SearchViewModel = viewModel(),
    resultsViewModel: SearchResultsViewModel = viewModel()
) {
    val query by viewModel.query.collectAsState()
    val selectedType by viewModel.selectedType.collectAsState()

    val popularItems by resultsViewModel.results.collectAsState()
    val isLoading by resultsViewModel.isLoading.collectAsState()

    LaunchedEffect(selectedType) {
        resultsViewModel.loadDefaultMedia(
            type = selectedType.ifBlank { null }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(R.string.app_name)) })

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (query.isNotBlank()) {
                    try {
                        val q = query
                        viewModel.clearQuery()
                        onSearch(q)
                    } catch (e: Exception) {
                        Log.e("SearchError", "Search failed: ${e.message}", e)
                    }
                }
            })
        )

        MediaTypeFilterChips(
            selectedType = selectedType,
            onTypeSelect = viewModel::onTypeSelect,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.search_popular_this_week
                ).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (isLoading && popularItems.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                popularItems.forEach { media ->
                    MediaResultCard(
                        media = media,
                        onClick = {
                            onMediaClick(media.id)
                        }
                    )
                }
            }
        }
    }
}