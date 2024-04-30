package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin (
    val loginId: String,
    val password: String
)