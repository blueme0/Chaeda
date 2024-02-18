package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class PresignedInfo (
    val imageKey: String,
    val presigendUrl: String
)