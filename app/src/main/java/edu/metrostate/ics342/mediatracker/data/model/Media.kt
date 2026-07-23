package edu.metrostate.ics342.mediatracker.data.model

import android.content.Context
import edu.metrostate.ics342.mediatracker.R

data class Media(
    val id: Int,
    val mediaType: MediaType, // "book", "movie", or "show"
    val title: String,
    val author: String? = null,       // books
    val director: String? = null,     // movies
    val creator: String? = null,      // shows
    val network: String? = null,      // shows (streaming / broadcast platform)
    val coverUrl: String? = null,
    val publishedYear: Int? = null,
    val averageRating: Float,
    val ratingCount: Int = 0,
    val genres: List<String> = emptyList(),
    val description: String
)

/** Returns a human-readable credit line appropriate for the media type. */
fun Media.creatorCredit(context: Context): String = when (mediaType) {
    MediaType.BOOK -> author ?: context.getString(R.string.media_unknown_author)
    MediaType.MOVIE -> director ?: context.getString(R.string.media_unknown_director)
    MediaType.SHOW -> creator ?: context.getString(R.string.media_unknown_creator)
    else -> ""
}