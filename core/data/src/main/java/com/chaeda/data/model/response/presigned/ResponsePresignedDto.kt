package com.chaeda.data.model.response.presigned

import com.chaeda.domain.entity.PresignedInfo
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePresignedDto (
    val imageKey: String,
    val presignedUrl: String
) {
    fun toPresignedInfo(): PresignedInfo = PresignedInfo(imageKey, presignedUrl)
}