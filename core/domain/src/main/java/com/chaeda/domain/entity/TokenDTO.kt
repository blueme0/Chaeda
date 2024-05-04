package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO (
    val accessTokenDto: String,
    val refreshTokenDto: String
)