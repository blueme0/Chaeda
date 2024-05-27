package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewFolderDTO (
    val title: String,
    val description: String,
    val createdDate: String?,
    val id: Long?
)

/**
 * {
 *   "reviewNoteProblemIds": "{1,2,3,4,5,6}",
 *   "title": "오답노트 제목",
 *   "description": "오답노트 간단 설명"
 * }
 */