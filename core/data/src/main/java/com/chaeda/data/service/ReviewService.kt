package com.chaeda.data.service

import com.chaeda.data.model.request.RequestReviewFolder
import com.chaeda.data.model.request.RequestReviewProblemDTO
import com.chaeda.data.model.response.PresignedUrlResponse
import com.chaeda.domain.entity.ReviewFolderDTO
import com.chaeda.domain.entity.ReviewPdfDTO
import com.chaeda.domain.entity.ReviewProblemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @POST("/review-note/problem")
    suspend fun postProblemToBox(
        @Body reviewProblem: RequestReviewProblemDTO
    ): Response<Unit>

    @POST("/review-note/folder")
    suspend fun postNewFolder(
        @Body folder: RequestReviewFolder
    ): Long

    @GET("/review-note/problem/list")
    suspend fun getProblemsFromBox(): List<ReviewProblemDTO>

    @POST("/review-note/pdf/{reviewNoteFolderId}")
    suspend fun postMakeReviewPdf(
        @Path ("reviewNoteFolderId") id: Long
    ): Long

    @GET("/review-note/pdf/{reviewNotePDFId}")
    suspend fun getReviewPdf(
        @Path ("reviewNotePDFId") id: Long
    ): PresignedUrlResponse

    @GET("/review-note/pdf/list")
    suspend fun getReviewPdfList(): List<ReviewPdfDTO>

    @GET("/review-note/folder/list")
    suspend fun getReviewFolderList(): List<ReviewFolderDTO>

    @GET("/review-note/folder/{folderId}/problem/list")
    suspend fun getProblemsInFolder(
        @Path ("folderId") id: Long
    ): List<ReviewProblemDTO>
}