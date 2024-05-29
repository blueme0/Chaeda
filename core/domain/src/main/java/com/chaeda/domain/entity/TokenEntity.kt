package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TokenEntity (
    val accessTokenDto: String,
    val refreshTokenDto: String
)