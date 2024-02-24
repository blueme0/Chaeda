package com.chaeda.domain.entity

data class PresignedDetailInfo (
    val presignedInfo: PresignedInfo,
    val imageType: String,
    val imageFileExtension: String
)