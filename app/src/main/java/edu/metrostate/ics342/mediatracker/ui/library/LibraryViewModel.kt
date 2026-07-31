package edu.metrostate.ics342.mediatracker.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val mediaRepository =
        DefaultMediaRepository(
            DefaultSessionRepository(application)
        )

    private val _libraryItems = MutableStateFlow<List<LibraryItem>>(emptyList())
    val libraryItems: StateFlow<List<LibraryItem>> = _libraryItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _filterState = MutableStateFlow(value = LibraryStatus.WANT_TO)
    val filterState: StateFlow<LibraryStatus> = _filterState.asStateFlow()

    //priorities state
    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities: StateFlow<List<Priority>> = _priorities.asStateFlow()

    private val _prioritiesLoading = MutableStateFlow(false)
    val prioritiesLoading: StateFlow<Boolean> = _prioritiesLoading.asStateFlow()

    init {
        loadLibrary()
        loadPriorities()
    }

    fun loadLibrary() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val page = mediaRepository.getLibrary(
                    status = null
                )

                _libraryItems.value = page.items
            } catch (e: Exception) {
                _libraryItems.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPriorities(){
        viewModelScope.launch {
            _prioritiesLoading.value = true

            try{
                _priorities.value = mediaRepository.getPriorities()
            } catch (e: Exception){
                _priorities.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeItem(mediaId: Int) {
        viewModelScope.launch {
            try {
                val success = mediaRepository.removeFromLibrary(mediaId)

                if (success) {
                    loadLibrary()
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    fun updateStatus(
        mediaId: Int,
        newStatus: LibraryStatus
    ) {
        viewModelScope.launch {
            try {
                val updated = mediaRepository.updateLibraryStatus(
                    mediaId,
                    newStatus
                )

                if (updated != null) {
                    loadLibrary()
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    fun updateFilter(status: LibraryStatus) {
        _filterState.value = status
    }

    fun updatePriority(
        mediaId: Int,
        priority: Int,
        orderIndex: Int,
        estimatedTimeHours: Int,
        notes: String
    ) {
        viewModelScope.launch {
            try {
                val updated = mediaRepository.updatePriority(
                    mediaId = mediaId,
                    priority = priority,
                    orderIndex = orderIndex,
                    estimatedTimeHours = estimatedTimeHours,
                    notes = notes
                )
                if (updated != null) {
                    loadPriorities()
                }
            } catch (e: Exception) {
                // error
            }
        }
    }
}
