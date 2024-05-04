package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentDTO (
    val title: String,
    val startPage: Int,
    val endPage: Int,
    val targetDate: String,
    val textbook: TextbookDTO?
)