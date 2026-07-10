package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.SessionRepository
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail

class DefaultMediaRepository(
    private val sessionRepository: SessionRepository
) {
    private val service: MediaApiService = RetrofitInstance.mediaApiService(sessionRepository)

    suspend fun getMediaById(mediaId: Int): MediaDetail? {
        return try {
            val response = service.getMediaById(mediaId)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}
sealed interface MediaDetailResult {
    data class Success(val media: MediaDetail) : MediaDetailResult
    data object NotFound : MediaDetailResult
    data object NetworkError : MediaDetailResult
    data object UnknownError : MediaDetailResult
}