package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.MediaNotFoundException
import edu.metrostate.ics342.mediatracker.data.model.Review
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MediaDetailUiState {
    data object Loading : MediaDetailUiState
    data object NotFound : MediaDetailUiState
    data class Error(val message: String) : MediaDetailUiState
    data class Success(
        val detail: MediaDetail,
        val libraryStatus: LibraryStatus?,
        val reviews: List<Review>
    ) : MediaDetailUiState
}

class MediaDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _uiState = MutableStateFlow<MediaDetailUiState>(MediaDetailUiState.Loading)
    val uiState: StateFlow<MediaDetailUiState> = _uiState.asStateFlow()

    private var currentMediaId: Int? = null

    fun load(mediaId: Int) {
        currentMediaId = mediaId
        _uiState.value = MediaDetailUiState.Loading

        viewModelScope.launch {
            try {
                // Uses the mediaId passed directly from the screen/navigation.
                val detail = repository.getMediaDetail(mediaId)

                val libraryItem = repository.getLibraryItem(mediaId)
                val reviews = repository.getReviews(mediaId)

                _uiState.value = MediaDetailUiState.Success(
                    detail = detail,
                    libraryStatus = libraryItem?.status,
                    reviews = reviews
                )

            } catch (e: MediaNotFoundException) {
                _uiState.value = MediaDetailUiState.NotFound

            } catch (e: Exception) {
                _uiState.value = MediaDetailUiState.Error(
                    e.message ?: "unable to load media"
                )
            }
        }
    }
    fun addToLibrary(){
        val mediaId = currentMediaId ?: return
        viewModelScope.launch {
            try {
                repository.addToLibrary(mediaId, LibraryStatus.WANT_TO)
                load(mediaId)
            } catch (e: Exception) {
                _uiState.value = MediaDetailUiState.Error(
                    e.message ?: "unable to add to library"
                )
            }
        }
    }

    fun updateLibraryStatus(status: LibraryStatus){
        val mediaId = currentMediaId ?: return

        viewModelScope.launch {
            try {
                repository.updateLibraryStatus(mediaId, status)
                load(mediaId)
            } catch (e: Exception) {
                _uiState.value = MediaDetailUiState.Error(
                    e.message ?: "unable to update status"
                )
            }
        }
    }

    fun removeFromLibrary() {
        val mediaId = currentMediaId ?: return

        viewModelScope.launch {
            try {
                repository.removeFromLibrary(mediaId)
                load(mediaId)
            } catch (e: Exception) {
                _uiState.value = MediaDetailUiState.Error(
                    e.message ?: "unable to remove media"
                )
            }
        }
    }
    fun addToFavorites() {
        val mediaId = currentMediaId ?: return
        viewModelScope.launch {
            try {
                repository.addToFavorites(mediaId)
            } catch (e: Exception) {
                _uiState.value = MediaDetailUiState.Error(
                    e.message ?: "unable to add to favorites"
                )
            }
        }
    }
}