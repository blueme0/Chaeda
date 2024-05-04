package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestAssignmentDTO(
    val title: String,
    val startPage: Int,
    val endPage: Int,
    val targetDate: String,
    val textbookId: Int
)