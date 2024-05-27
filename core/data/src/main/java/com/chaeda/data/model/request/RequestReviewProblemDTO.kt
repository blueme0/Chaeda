package com.chaeda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewProblemDTO (
    val incorrectDate: String,
    val imageKey: String,
    val fileExtension: String,
    val answer: String,
    val textbookName: String,
    val problemNum: String,
    val chapter: String
)

/**
 * /**
 *  * {
 *  *   "incorrectDate": "2024-05-22",
 *  *   "imageKey": "10a99bab-4940-48af-92e7-867a56d6ec79",
 *  *   "imageFileExtension": "PNG",
 *  *   "answer": "42",
 *  *   "textbookId": 1,
 *  *   "problemNum": "101",
 *  *   "chapter": "Polynomial"
 *  * }
 *  */
 *
 */