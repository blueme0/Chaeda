package com.chaeda.data.model.request.textbook

import kotlinx.serialization.Serializable

@Serializable
data class RequestTextbookDto (
    val name: String,
    val startPageNum: Int,
    val lastPageNum: Int,
    val publisher: String,
    val targetGrade: String,
    val subject: String,
    val publishYear: Int,
    val fileExtension: String
)

/**
 * {
 *   "name": "쎈_고등수학상_2024",
 *   "startPageNum": 11,
 *   "lastPageNum": 224,
 *   "publisher": "좋은책 신사고",
 *   "targetGrade": "고1",
 *   "subject": "수학1",
 *   "publishYear": 2024,
 *   "fileExtension": "pdf"
 * }
 */