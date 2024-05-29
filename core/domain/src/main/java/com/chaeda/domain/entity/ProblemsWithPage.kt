package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProblemsWithPage (
    val pageNum: Int,
    val problemNumbers: List<String>
)