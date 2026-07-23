package edu.metrostate.ics342.mediatracker.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import edu.metrostate.ics342.mediatracker.R
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail

/* fake test book
private val fakeMedia = Media(
    id = 1,
    mediaType = MediaType.BOOK,
    title = "The Hitchhiker's Guide to the Galaxy",
    author = "Douglas Adams",
    publishedYear = 1979,
    averageRating = 4.7f,
    ratingCount = 312,
    description = "A comedic science fiction series.",
    genres = listOf("Science Fiction", "Comedy"),
    director = null,
    creator = null,
    network = null,
    coverUrl = "https://tse3.mm.bing.net/th/id/OIP.GLsNxZvhySpFQu71sMCV7wHaLX?r=0&cb=thfc1falcon4&rs=1&pid=ImgDetMain&o=7&rm=3",
)
*/


// ── STUB — Students build this in Week 7 ─────────────────────────────────────
//
// Week 7 task: Build the Media Detail screen.
//   1. Receive mediaId from the navigation argument (typed Int — see NavGraph).
//   2. Call GET /media/{mediaId} to load full details.
//   3. Display: cover image, title, creator credit, metadata grid, genre chips,
//      average rating, description, and a library status control.
//   4. Display the reviews list from GET /reviews?mediaId={id}.
//   5. Handle loading and error states (full-screen — no half-built screens).
    @SuppressLint("DefaultLocale")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MediaDetailScreen(
        mediaId: Int,
        onNavigateBack: () -> Unit,
        onWriteReview: (Int) -> Unit,
        viewModel: MediaDetailViewModel = viewModel()
    ) {

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(mediaId) {
        viewModel.load(mediaId)
    }

    when (uiState) {
        is MediaDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MediaDetailUiState.Success -> {
            val detail = (uiState as MediaDetailUiState.Success).detail

            Column {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* overflow menu */ }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More"
                            )
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(24.dp))

                    // AsyncImage Url is null, possible cause of app crash
                    AsyncImage(
                        model = detail.coverUrl,
                        contentDescription = "Cover",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(Modifier.height(16.dp))


                    Text(
                        text = detail.author ?: detail.director ?: "Unknown",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = buildString {
                                append(" ${String.format("%.1f", detail.averageRating)}")
                                append(" • ${detail.mediaType.replaceFirstChar { it.uppercase() }}")
                                detail.publishedYear?.let { append(" • $it") }
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* add library */ },
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(size = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                            Text("Want to")
                        }

                        OutlinedButton(
                            onClick = { /*save */ },
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(size = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite"
                            )
                            Text("Save")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "About",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray
                    )

                    Text(
                        text = detail.description ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }
        } // end success

        is MediaDetailUiState.NotFound -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Media not found")
            }
        }

        is MediaDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text((uiState as MediaDetailUiState.Error).message)
            }
        }
    }
}
/*
    @Preview
    @Composable
    fun MediaDetailScreenPreview() {
        MediaDetailScreen(
            mediaId = 1,
            onNavigateBack = {},
            onWriteReview = {}
        )
    }
*/
