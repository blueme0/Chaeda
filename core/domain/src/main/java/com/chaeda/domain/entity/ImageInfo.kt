package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ImageInfo (
    val imageType: String,
    val fileExtension: String,
    val imageKey: String
)