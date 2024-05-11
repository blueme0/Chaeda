package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ConceptDetailDTO (
    val subject: String, //
    val chapter: String,
    val subConcept: String,
    val problemCount: Int, // 푼 문항 수
    val wrongCount: Int, // 틀린 문항 수
    val easyNum: Int, // 하 난이도로 느낀 틀린문제 수
    val middleNum: Int, // 중 난이도로 느낀 틀린문제 수
    val hardNum: Int // 상 난이도로 느낀 틀린문제 수
)