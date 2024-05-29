package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewPdf (
    val id: Long,
    val title: String,
    val createdDateTime: String
)