package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentDTO (
    val id: Long?,
    val title: String,
    val startPage: Int,
    val endPage: Int,
    val targetDate: String,
    val textbook: TextbookDTO?,
    val isCompleted: Boolean?
)