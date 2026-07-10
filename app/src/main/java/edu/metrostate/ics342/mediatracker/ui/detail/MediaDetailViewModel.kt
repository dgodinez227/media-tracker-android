package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.data.network.MediaDetailResult
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDetailViewModel(
    application: Application,
    private val repository: DefaultMediaRepository = DefaultMediaRepository(
        DefaultSessionRepository(application.applicationContext)
    )
) : AndroidViewModel(application) {
    // TODO (Week 7): Accept mediaId, call GET /media/{id}, expose MediaDetail state.
    // Also call GET /library to load current status for this item.
  /*  private val _mediaId = MutableStateFlow(-1)
    val mediaId: StateFlow<Int> = _mediaId.asStateFlow()

    fun setMediaId(id: Int) { _mediaId.value = id } */
   // diff Ui states
    sealed class UiState {
        data object Loading : UiState()
        data class Success(val media: MediaDetail) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadMedia(mediaId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            when (val result = repository.getMediaById(mediaId)) {
                is MediaDetailResult.Success -> {
                    _uiState.value = UiState.Success(result.media)
                }

                MediaDetailResult.NotFound -> {
                    _uiState.value = UiState.Error("Not found")
                }

                MediaDetailResult.NetworkError -> {
                    _uiState.value =
                        UiState.Error("network erorr")
                }

                MediaDetailResult.UnknownError -> {
                    _uiState.value =
                        UiState.Error("unknown error")
                }
            }
        }
    }
}
