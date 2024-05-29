package com.chaeda.domain.repository

import com.chaeda.domain.entity.ReviewFolder
import com.chaeda.domain.entity.ReviewPdf
import com.chaeda.domain.entity.ReviewProblem
import com.chaeda.domain.enumSet.Chapter

interface ReviewRepository {
    suspend fun postProblemToBox(
        reviewProblem: ReviewProblem,
        fileExtension: String,
        answer: String,
        textbookName: String,
        problemNum: String,
        chapter: Chapter
    ): Result<Unit>
    suspend fun postNewFolder(
        problemsIds: List<Long>,
        reviewFolder: ReviewFolder
    ): Result<Long>
    suspend fun getProblemsFromBox(): Result<List<ReviewProblem>>
    suspend fun postMakeReviewPdf(folderId: Long): Result<Long>
    suspend fun getReviewPdf(pdfId: Long): Result<String>
    suspend fun getReviewPdfList(): Result<List<ReviewPdf>>
    suspend fun getReviewFolderList(): Result<List<ReviewFolder>>
    suspend fun getProblemsInFolder(folderId: Long): Result<List<ReviewProblem>>
}