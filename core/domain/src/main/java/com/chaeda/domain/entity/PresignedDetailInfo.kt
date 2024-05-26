package com.chaeda.domain.entity

import javax.inject.Named

data class PresignedDetailInfo (
    val presignedInfo: PresignedInfo,
    val imageType: String,
    @Named("fileExtension")
    val imageFileExtension: String
)