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

internal const val MAX_PRIORITIES = 5

internal fun canAddPriority(priorityCount: Int): Boolean {
    return priorityCount < MAX_PRIORITIES
}

enum class PriorityError {
    LOAD_FAILED,
    UPDATE_FAILED,
    MAX_REACHED
}
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
    private val _priorityError =
        MutableStateFlow<PriorityError?>(null)

    val priorityError: StateFlow<PriorityError?> =
        _priorityError.asStateFlow()
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

    fun loadPriorities() {
        viewModelScope.launch {
            _prioritiesLoading.value = true
            _priorityError.value = null
            try {
                _priorities.value = mediaRepository.getPriorities()
            } catch (e: Exception) {
                _priorities.value = emptyList()
                _priorityError.value =
                    PriorityError.LOAD_FAILED
            } finally {
                _prioritiesLoading.value = false
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

        if (priority !in 1..3) {
            _priorityError.value =
                PriorityError.UPDATE_FAILED
            return
        }

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
                } else {
                    _priorityError.value =
                        PriorityError.UPDATE_FAILED
                }
            } catch (e: Exception) {
                _priorityError.value =
                    PriorityError.UPDATE_FAILED
            }
        }
    }

    fun addPriority(
        mediaId: Int,
        priority: Int,
        estimatedTimeHours: Int,
        notes: String
    ) {
        val currentPriorities = _priorities.value

        val existingPriority =
            currentPriorities.find {
                it.mediaId == mediaId
            }

        if (existingPriority != null) {
            updatePriority(
                mediaId = existingPriority.mediaId,
                priority = priority,
                orderIndex = existingPriority.orderIndex,
                estimatedTimeHours = estimatedTimeHours,
                notes = notes
            )
            return
        }

        if (!canAddPriority(currentPriorities.size)) {
            _priorityError.value = PriorityError.MAX_REACHED
            return
        }

        val nextOrderIndex =
            (currentPriorities.maxOfOrNull {
                it.orderIndex
            } ?: -1) + 1

        updatePriority(
            mediaId = mediaId,
            priority = priority,
            orderIndex = nextOrderIndex,
            estimatedTimeHours = estimatedTimeHours,
            notes = notes
        )
    }
    fun reorderPriorities(
        fromMediaId: Int,
        toMediaId: Int
    ) {

        if (fromMediaId == toMediaId) return

        val current =
            _priorities.value
                .sortedBy { it.orderIndex }
                .toMutableList()

        val fromIndex =
            current.indexOfFirst {
                it.mediaId == fromMediaId
            }

        val toIndex =
            current.indexOfFirst {
                it.mediaId == toMediaId
            }

        if (
            fromIndex == -1 ||
            toIndex == -1
        ) {
            return
        }

        val movedItem =
            current.removeAt(fromIndex)

        current.add(
            toIndex,
            movedItem
        )

        val reordered =
            current.mapIndexed { index, item ->
                item.copy(
                    orderIndex = index
                )
            }
        _priorities.value = reordered

        viewModelScope.launch {
            _priorityError.value = null

            try {

                reordered.forEach { item ->

                    val updated =
                        mediaRepository.updatePriority(
                            mediaId = item.mediaId,
                            priority = item.priority,
                            orderIndex = item.orderIndex,
                            estimatedTimeHours =
                                item.estimatedTimeHours,
                            notes = item.notes
                        )

                    if (updated == null) {
                        throw IllegalStateException(
                            "Priority reorder failed"
                        )
                    }
                }
                loadPriorities()

            } catch (e: Exception) {

                _priorityError.value =
                    PriorityError.UPDATE_FAILED
                loadPriorities()
            }
        }
    }
    fun clearPriorityError() {
        _priorityError.value = null
    }
}
