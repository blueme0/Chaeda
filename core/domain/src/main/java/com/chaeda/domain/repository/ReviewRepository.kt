package com.chaeda.domain.repository

import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewPdfDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import com.chaeda.domain.enumSet.Chapter

interface ReviewRepository {
    suspend fun postProblemToBox(
        reviewProblem: ReviewProblemDTO,
        fileExtension: String,
        answer: String,
        textbookName: String,
        problemNum: String,
        chapter: Chapter
    ): Result<Unit>
    suspend fun postNewFolder(
        problemsIds: Set<Long>,
        reviewFolder: ReviewFolderDTO
    ): Result<Long>
    suspend fun getProblemsFromBox(): Result<List<ReviewProblemDTO>>
    suspend fun postMakeReviewPdf(folderId: Long): Result<Long>
    suspend fun getReviewPdf(pdfId: Long): Result<String>
    suspend fun getReviewPdfList(): Result<List<ReviewPdfDTO>>
    suspend fun getReviewFolderList(): Result<List<ReviewFolderDTO>>
    suspend fun getProblemsInFolder(folderId: Long): Result<List<ReviewProblemDTO>>
}