package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class WrongCountWithConcept (
    val typeId: Int, // 개념 id
    val subConcept: String, // 세부 개념,
    val problemCount: Int, // 푼 문항 수
    val wrongCount: Int // 틀린 문항 수
)