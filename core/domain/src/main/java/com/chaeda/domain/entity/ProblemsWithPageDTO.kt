package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProblemsWithPageDTO (
    val pageNum: Int,
    val problemNumbers: List<String>
)