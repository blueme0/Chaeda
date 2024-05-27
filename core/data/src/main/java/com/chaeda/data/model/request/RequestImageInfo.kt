package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestImageInfo (
    val imageType: String,
    val fileExtension: String
)