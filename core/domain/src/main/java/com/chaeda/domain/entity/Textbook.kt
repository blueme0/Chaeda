package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Textbook (
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val targetGrade: String,
    val subject: String,
    val publishYear: Int,
    val publisher: String,
    val startPage: Int,
    val lastPage: Int
)

/**
 * [
 *   {
 *     "id": 1,
 *     "name": "쎈_고등수학상",
 *     "imageUrl": "https://s3-fullaccel.s3.ap-northeast-2.amazonaws.com/textbook_profile/%E1%84%8A%E1%85%A6%E1%86%AB_%E1%84%89%E1%85%AE%E1%84%92%E1%85%A1%E1%86%A8%E1%84%89%E1%85%A1%E1%86%BC_2024.jpeg",
 *     "targetGrade": "고1",
 *     "subject": "수학 상",
 *     "publishYear": 2024,
 *     "publisher": "좋은책 신사고",
 *     "startPage": 10,
 *     "lastPage": 210
 *   }
 * ]
 */