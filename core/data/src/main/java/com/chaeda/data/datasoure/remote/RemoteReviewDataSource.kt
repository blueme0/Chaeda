package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestReviewFolder
import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.data.service.ReviewService
import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewPdfDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import javax.inject.Inject

class RemoteReviewDataSource @Inject constructor(
    private val reviewService: ReviewService,
){
    suspend fun postProblemToBox(
        problem: RequestReviewProblemDTO
    ): Boolean {
        return reviewService.postProblemToBox(problem).isSuccessful
    }

    suspend fun postNewFolder(
        problemsIds: Set<Long>,
        folder: ReviewFolderDTO
    ): Long {
        return reviewService.postNewFolder(
            RequestReviewFolder(
                problemsIds,
                folder.title,
                folder.description
            )
        )
    }

    suspend fun getProblemsFromBox(): List<ReviewProblemDTO> {
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

    suspend fun getReviewPdfList(): List<ReviewPdfDTO> {
        return reviewService.getReviewPdfList()
    }

    suspend fun getReviewFolderList(): List<ReviewFolderDTO> {
        return reviewService.getReviewFolderList()
    }

    suspend fun getProblemsInFolder(
        folderId: Long
    ): List<ReviewProblemDTO> {
        return reviewService.getProblemsInFolder(folderId)
    }
}