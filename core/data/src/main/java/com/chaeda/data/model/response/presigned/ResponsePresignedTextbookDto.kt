package com.chaeda.data.model.response.presigned

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePresignedTextbookDto (
    val presignedUrl: String,
    val fileName: String
)

/**
 * {
 *   "presignedUrl": "string",
 *   "fileName": "textbook/2024/쎈수학상.pdf"
 * }
 */