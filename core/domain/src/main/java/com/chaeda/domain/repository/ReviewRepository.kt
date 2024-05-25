package com.chaeda.domain.repository

import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import com.chaeda.domain.enumSet.Chapter

interface ReviewRepository {
    suspend fun postProblemToBox(
        reviewProblem: ReviewProblemDTO,
        imageFileExtension: String,
        answer: String,
        textbookId: Int,
        problemNum: String,
        chapter: Chapter
    ): Result<Unit>
    suspend fun postNewFolder(reviewFolder: ReviewFolderDTO): Result<Long>
    suspend fun getProblemsFromBox(): Result<List<ReviewProblemDTO>>
}