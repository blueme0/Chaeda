package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO (
    val accessTokenDTO: String,
    val refreshTokenDTO: String
)