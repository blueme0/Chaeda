package com.chaeda.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PresignedUrlResponse (
    val presignedUrl: String
)