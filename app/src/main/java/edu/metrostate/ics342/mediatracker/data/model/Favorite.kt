package edu.metrostate.ics342.mediatracker.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val userId: String,
    val mediaId: Int,
    val createdAt: String,
    // contextual for custom media type
    @Contextual
    val media: Media? = null
)