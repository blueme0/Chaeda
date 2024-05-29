package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ChapterDetail(
    val chapterId: Int,
    val chapterName: String,
    val problemCount: Int, // 푼 문항 수
    val wrongCount: Int // 틀린 문항 수
)