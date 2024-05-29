package com.chaeda.data.model.response.presigned

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePresignedUrlDto (
    val presignedUrl: String
)