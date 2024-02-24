package com.chaeda.data.model.response

import com.chaeda.domain.entity.PresignedInfo
import kotlinx.serialization.Serializable

@Serializable
data class PresignedResponse (
    val imageKey: String,
    val presignedUrl: String
) {
    fun toPresignedInfo(): PresignedInfo = PresignedInfo(imageKey, presignedUrl)
}