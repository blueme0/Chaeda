package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.data.service.ReviewService
import com.chaeda.domain.entity.ReviewFolderDTO
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
        folder: ReviewFolderDTO
    ): Long {
        return reviewService.postNewFolder(folder)
    }

    suspend fun getProblemsFromBox(): List<ReviewProblemDTO> {
        return reviewService.getProblemsFromBox()
    }
}