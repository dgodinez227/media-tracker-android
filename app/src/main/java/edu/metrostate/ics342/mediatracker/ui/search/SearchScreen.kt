package edu.metrostate.ics342.mediatracker.ui.search

import android.app.appsearch.SearchResults
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
    onMediaClick: (Int) -> Unit,
    viewModel: SearchViewModel = viewModel(),
    onSearch: (String) -> Unit
) {
    val query by viewModel.query.collectAsState()
    val selectedType by viewModel.selectedType.collectAsState()

    val popularItems = FakeMediaRepository.mediaList.filter { media ->
        selectedType.isEmpty() || media.mediaType.apiString == selectedType
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(stringResource(R.string.app_name))
            })

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (query.isNotBlank()) {
                    val q = query
                    viewModel.clearQuery()
                    onSearch(q)
                }
            })
        )

        MediaTypeFilterChips(
            selectedType = selectedType,
            onTypeSelect = viewModel::onTypeSelect,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.search_popular_this_week).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(popularItems, key = { it.id }) { media ->
                Card(
                    onClick = { onMediaClick(media.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = media.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}