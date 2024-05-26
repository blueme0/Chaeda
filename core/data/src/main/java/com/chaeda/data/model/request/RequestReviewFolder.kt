package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewFolder(
    val reviewNoteProblemIds: Set<Long>,
    val title: String,
    val description: String
)