package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.SessionRepository
import edu.metrostate.ics342.mediatracker.data.model.AddToFavoritesRequest
import edu.metrostate.ics342.mediatracker.data.model.AddToLibraryRequest
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.Favorite
import edu.metrostate.ics342.mediatracker.data.network.UpdateLibraryStatusRequest
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.MediaNotFoundException
import edu.metrostate.ics342.mediatracker.data.model.Review

data class MediaPage(
    val items: List<Media>,
    val nextCursor: String?,
    val hasMore: Boolean
)
data class LibraryPage(
    val items: List<LibraryItem>,
    val nextCursor: String?,
    val hasMore: Boolean
)

class DefaultMediaRepository(sessionRepository: SessionRepository) {

    private val api = RetrofitInstance.mediaApiService(sessionRepository)

    suspend fun search(
        query: String,
        type: String?,
        after: String?
    ): MediaPage {
        val response = api.searchMedia(
            query = query.ifBlank { null },
            type = type?.ifBlank { null },
            after = after
        )

        if (!response.isSuccessful) {
            error("Search failed: HTTP ${response.code()}")
        }

        val items = response.body() ?: emptyList()
        val nextCursor = response.headers()["X-Next-Cursor"]
        val hasMore = response.headers()["X-Has-More"] == "true"

        return MediaPage(
            items = items,
            nextCursor = nextCursor,
            hasMore = hasMore
        )
    }

    suspend fun getMediaDetail(id: Int): MediaDetail {
        val response = api.getMediaDetail(id)
        if (response.code() == 404) {
            throw MediaNotFoundException("Media not found")
        }
        if (!response.isSuccessful) {
            error("Failed to load media")
        }
        return response.body() ?: error("Empty body")
    }

    suspend fun getReviews(mediaId: Int): List<Review> {
        val response = api.getReviews(mediaId)
        if (!response.isSuccessful) return emptyList()
        return response.body() ?: emptyList()
    }

    suspend fun getLibraryItem(mediaId: Int): LibraryItem? {
        val response = api.getLibraryItem(mediaId)
        if (response.code() == 404) return null
        if (!response.isSuccessful) return null
        return response.body()
    }

    suspend fun addToLibrary(mediaId: Int, status: LibraryStatus): LibraryItem? {
        val response = api.addToLibrary(
            AddToLibraryRequest(
                mediaId = mediaId,
                status = status.toApiString()
            )
        )

        if (!response.isSuccessful) return null
        return response.body()
    }

    suspend fun addToFavorites(mediaId: Int): Boolean {
        val response = api.addToFavorites(AddToFavoritesRequest(mediaId))
        return response.isSuccessful
    }

    suspend fun getFavorite(mediaId: Int): Favorite? {
        val response = api.getFavorite(mediaId)
        if (response.code() == 404) return null
        if (!response.isSuccessful) return null
        return response.body()
    }


    suspend fun getLibrary(status: LibraryStatus?, after: String? = null): LibraryPage {
        val response = api.getLibrary(
            status = status?.toApiString(),
            after = after
        )
        if (!response.isSuccessful) {
            error("Failed to load library")
        }
        val items = response.body() ?: emptyList()
        val nextCursor = response.headers()["X-Next-Cursor"]
        val hasMore = response.headers()["X-Has-More"] == "true"
        return LibraryPage(items, nextCursor, hasMore)
    }

    suspend fun removeFromLibrary(mediaId: Int): Boolean {
        val response = api.removeFromLibrary(mediaId)
        return response.isSuccessful
    }

    suspend fun updateLibraryStatus(mediaId: Int, status: LibraryStatus): LibraryItem? {
        val response = api.updateLibraryStatus(mediaId, UpdateLibraryStatusRequest(status))
        if (!response.isSuccessful) return null
        return response.body()
    }


}