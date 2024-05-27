package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewPdfDTO (
    val id: Long,
    val title: String,
    val createdDateTime: String
)