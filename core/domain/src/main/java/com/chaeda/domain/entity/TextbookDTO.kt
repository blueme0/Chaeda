package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TextbookDTO (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val targetGrade: String,
    val publishYear: Int,
    val publisher: String
)