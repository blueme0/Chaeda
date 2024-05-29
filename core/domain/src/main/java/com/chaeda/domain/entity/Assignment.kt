package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Assignment (
    val id: Long?,
    val title: String,
    val startPage: Int,
    val endPage: Int,
    val targetDate: String,
    val textbook: Textbook?,
    val isCompleted: Boolean?
)