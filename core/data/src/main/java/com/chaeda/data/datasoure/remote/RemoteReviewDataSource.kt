package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.review.RequestReviewFolderDto
import com.chaeda.data.model.request.review.RequestReviewProblemDto
import com.chaeda.data.service.ReviewService
import com.chaeda.domain.entity.ReviewFolder
import com.chaeda.domain.entity.ReviewPdf
import com.chaeda.domain.entity.ReviewProblem
import javax.inject.Inject

class RemoteReviewDataSource @Inject constructor(
    private val reviewService: ReviewService,
){
    suspend fun postProblemToBox(
        problem: RequestReviewProblemDto
    ): Boolean {
        return reviewService.postProblemToBox(problem).isSuccessful
    }

    suspend fun postNewFolder(
        problemsIds: List<Long>,
        folder: ReviewFolder
    ): Long {
        return reviewService.postNewFolder(
            RequestReviewFolderDto(
                problemsIds,
                folder.title,
                folder.description
            )
        )
    }

    suspend fun getProblemsFromBox(): List<ReviewProblem> {
        return reviewService.getProblemsFromBox()
    }

    suspend fun postMakeReviewPdf(
        folderId: Long
    ): Long {
        return reviewService.postMakeReviewPdf(folderId)
    }

    suspend fun getReviewPdf(
        pdfId: Long
    ): String {
        return reviewService.getReviewPdf(pdfId).presignedUrl
    }

    suspend fun getReviewPdfList(): List<ReviewPdf> {
        return reviewService.getReviewPdfList()
    }

    suspend fun getReviewFolderList(): List<ReviewFolder> {
        return reviewService.getReviewFolderList()
    }

    suspend fun getProblemsInFolder(
        folderId: Long
    ): List<ReviewProblem> {
        return reviewService.getProblemsInFolder(folderId)
    }
}