package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewFolder(
    val reviewNoteProblemIds: List<Long>,
    val title: String,
    val description: String
)