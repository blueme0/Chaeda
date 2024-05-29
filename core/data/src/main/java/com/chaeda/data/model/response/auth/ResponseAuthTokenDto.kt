package com.chaeda.data.model.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResponseAuthTokenDto(
    val accessToken: String,
    val refreshToken: String,
)