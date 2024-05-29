package com.chaeda.data.model.request.review

import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewFolderDto(
    val reviewNoteProblemIds: List<Long>,
    val title: String,
    val description: String
)