package com.chaeda.data.service

import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReviewService {
    @POST("/review-note/problem")
    suspend fun postProblemToBox(
        @Body reviewProblem: RequestReviewProblemDTO
    ): Response<Unit>

    @POST("/review-note/folder")
    suspend fun postNewFolder(
        @Body folder: ReviewFolderDTO
    ): Long

    @GET("/review-note/problem/list")
    suspend fun getProblemsFromBox(): List<ReviewProblemDTO>
}