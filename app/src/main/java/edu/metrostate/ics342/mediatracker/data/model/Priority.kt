package edu.metrostate.ics342.mediatracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Priority(
    val mediaId: Int,
    val priority: Int,
    val orderIndex: Int,
    val estimatedTimeHours: Int,
    val notes: String,
    val media: Media
)

@Serializable
data class UpdatePriorityRequest(
    val mediaId: Int,
    val priority: Int,
    val orderIndex: Int,
    val estimatedTimeHours: Int,
    val notes: String
)
