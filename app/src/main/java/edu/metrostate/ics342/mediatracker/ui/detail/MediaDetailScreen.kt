package edu.metrostate.ics342.mediatracker.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import kotlin.math.roundToInt

/* fake test book
private val fakeMedia = Media(
    id = 1,
    mediaType = "book",
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

    when (val state = uiState) {
        MediaDetailUiState.Loading -> {
            CircularProgressIndicator()
        }

        MediaDetailUiState.NotFound -> {
            Text("Media not found")
        }

        is MediaDetailUiState.Error -> {
            Text(state.message)
        }

        is MediaDetailUiState.Success -> {
            val detail = state.detail
            val libraryStatus = state.libraryStatus
            val reviews = state.reviews

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
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(24.dp))

                    AsyncImage(
                        model = detail.coverUrl ?: "",
                        contentDescription = "Cover",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = detail.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = detail.author
                            ?: detail.director
                            ?: detail.creator
                            ?: "Unknown",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(8.dp))

                    RatingSummary(
                        averageRating = detail.averageRating,
                        ratingCount = detail.ratingCount
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { viewModel.addToLibrary() },
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
                            onClick = { viewModel.addToFavorites() },
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
                        text = "ABOUT",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = detail.description ?: "No description available.",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetadataCard(
                            label = "YEAR",
                            value = detail.publishedYear?.toString() ?: "N/A",
                            modifier = Modifier.weight(1f)
                        )

                        MetadataCard(
                            label = getMediaTypeLabel(detail),
                            value = getMediaTypeValue(detail),
                            modifier = Modifier.weight(1f)
                        )

                        MetadataCard(
                            label = "GENRE",
                            value = detail.genres.firstOrNull() ?: "N/A",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "REVIEWS (${detail.reviewCount ?: reviews.size})",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        TextButton(
                            onClick = {
                                onWriteReview(detail.id)
                            }
                        ) {
                            Text("+ Write Review")
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    if (reviews.isEmpty()) {

                        Text(
                            text = "No reviews yet.",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    } else {

                        reviews.forEach { review ->

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = review.userId,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    StarRow(rating = review.rating.toFloat())

                                    Spacer(Modifier.height(8.dp))

                                    Text(
                                        text = review.reviewText ?: "No review provided.",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}
        @Composable
        private fun RatingSummary(
            averageRating: Float,
            ratingCount: Int
        ) {
            if (ratingCount <= 0) {
                Text(
                    text = "Not yet rated",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                return
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StarRow(rating = averageRating)

                Spacer(Modifier.width(6.dp))

                Text(
                    text = String.format("%.1f", averageRating),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = "($ratingCount)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

            @Composable

            private fun StarRow(
                rating: Float,
                starSize: Int =

                    16
            ) {
                val roundedRating = (rating * 2).roundToInt()

                Row {
                    for (star in 1..5) {
                        val icon = when {
                            roundedRating >= star * 2 ->
                                Icons.Filled.Star

                            roundedRating == star * 2 - 1 ->
                                Icons.Outlined.StarHalf

                            else ->
                                Icons.Outlined.StarBorder
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(starSize.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            @Composable
            private fun MetadataCard(
                label: String,
                value: String,
                modifier: Modifier = Modifier
            ) {
                Surface(
                    modifier = modifier,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 12.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            fun getMediaTypeLabel(media: MediaDetail)

                    : String =
                when (media.mediaType) {
                    "book" -> "PAGES"
                    "movie" -> "RUNTIME"
                    "show" -> "SEASONS"
                    else -> "TYPE"
                }

            fun getMediaTypeValue(media: MediaDetail): String =
                when (media.mediaType) {
                    "book" ->
                        media.pageCount?.toString() ?: "N/A"

                    "movie" ->
                        media.runtimeMinutes?.let { "$it min" } ?: "N/A"

                    "show" ->
                        media.seasonCount?.toString()
                            ?: media.episodeCount?.toString()
                            ?: "N/A"

                    else ->
                        media.mediaType.replaceFirstChar { it.uppercase() }
                }