package com.chaeda.data.model.request.image

import kotlinx.serialization.Serializable

@Serializable
data class RequestImageInfoDto (
    val imageType: String,
    val fileExtension: String
)