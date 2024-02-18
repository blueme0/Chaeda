package com.chaeda.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PresignedResponse (
    val imageKey: String,
    val presigendUrl: String
)