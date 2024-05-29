package com.chaeda.data.service

import com.chaeda.data.model.request.review.RequestReviewFolderDto
import com.chaeda.data.model.request.review.RequestReviewProblemDto
import com.chaeda.data.model.response.presigned.ResponsePresignedUrlDto
import com.chaeda.domain.entity.ReviewFolder
import com.chaeda.domain.entity.ReviewPdf
import com.chaeda.domain.entity.ReviewProblem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @POST("/review-note/problem")
    suspend fun postProblemToBox(
        @Body reviewProblem: RequestReviewProblemDto
    ): Response<Unit>

    @POST("/review-note/folder")
    suspend fun postNewFolder(
        @Body folder: RequestReviewFolderDto
    ): Long

    @GET("/review-note/problem/list")
    suspend fun getProblemsFromBox(): List<ReviewProblem>

    @POST("/review-note/pdf/{reviewNoteFolderId}")
    suspend fun postMakeReviewPdf(
        @Path ("reviewNoteFolderId") id: Long
    ): Long

    @GET("/review-note/pdf/{reviewNotePDFId}")
    suspend fun getReviewPdf(
        @Path ("reviewNotePDFId") id: Long
    ): ResponsePresignedUrlDto

    @GET("/review-note/pdf/list")
    suspend fun getReviewPdfList(): List<ReviewPdf>

    @GET("/review-note/folder/list")
    suspend fun getReviewFolderList(): List<ReviewFolder>

    @GET("/review-note/folder/{folderId}/problem/list")
    suspend fun getProblemsInFolder(
        @Path ("folderId") id: Long
    ): List<ReviewProblem>
}