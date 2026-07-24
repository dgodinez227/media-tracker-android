package edu.metrostate.ics342.mediatracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AddToFavoritesRequest(
    val mediaId: Int
)
