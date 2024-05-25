package com.chaeda.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewProblemDTO (
    val reviewNoteProblemId: Long?,
    val incorrectDate: String,
    val imageKey: String,
    val presignedUrl: String
)

/**
 * [
 *   {
 *     "reviewNoteProblemId": 1,
 *     "incorrectDate": "2024-05-22",
 *     "imageKey": "10a99bab-4940-48af-92e7-867a56d6ec79",
 *     "presignedUrl": "string"
 *   }
 * ]
 */