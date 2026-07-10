package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MediaApiService {

    @GET("media/{mediaId}")
    suspend fun getMediaById(
        @Path("mediaId") mediaId: Int
    ): Response<MediaDetail>
}