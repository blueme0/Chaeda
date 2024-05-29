package com.chaeda.data.model.request.assignment

import kotlinx.serialization.Serializable

@Serializable
data class RequestAssignmentDto(
    val title: String,
    val startPage: Int,
    val endPage: Int,
    val targetDate: String,
    val textbookId: Int
)