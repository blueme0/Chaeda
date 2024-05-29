package com.chaeda.data.model.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto (
    val loginId: String,
    val password: String
)