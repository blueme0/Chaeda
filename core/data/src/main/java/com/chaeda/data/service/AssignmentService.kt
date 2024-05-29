package com.chaeda.data.service

import com.chaeda.data.model.request.assignment.RequestAssignmentDto
import com.chaeda.data.model.request.assignment.RequestAssignmentResultDto
import com.chaeda.data.model.request.image.RequestImageInfoDto
import com.chaeda.data.model.response.presigned.ResponsePresignedDto
import com.chaeda.domain.entity.Assignment
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.ProblemsWithPage
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AssignmentService {

    /**
     * api related to images
     */

    @POST("/api/images/presigned-url")
    suspend fun getPresignedUrl(
        @Body requestImageInfo: RequestImageInfoDto
    ): ResponsePresignedDto

    @Multipart
    @POST("/api/images/upload") // 실제 API 엔드포인트를 여기에 입력
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): String

    @POST("/api/images/upload-complete")
    suspend fun noticePresignedUrl(
        @Body imageInfo: List<ImageInfo>
    ): ResponseBody

    @POST("/api/images/display")
    suspend fun getImagesUrl(
        @Body images: List<ImageInfo>
    ): List<String>

    /**
     * api related to assignment
     */

    @GET("/assignment/{assignmentId}")
    suspend fun getAssignmentById(
        @Path("assignmentId") assignmentId: Long
    ): Assignment

    @PUT("/assignment/{assignmentId}")
    suspend fun putAssignmentById(
        @Path("assignmentId") assignmentId: Long,
        @Body assignment: RequestAssignmentDto
    ): Assignment

    @DELETE("/assignment/{assignmentId}")
    suspend fun deleteAssignmentById(
        @Path("assignmentId") assignmentId: Long
    ): Response<Unit>

    @GET("/assignment")
    suspend fun getAssignmentsByDate(
        @Query("date") date: String
//        @Named("date") @Body d: LocalDate
    ): List<Assignment>

    @POST("/assignment")
    suspend fun postAssignment(
        @Body request: RequestAssignmentDto
    ): Assignment

    @GET("/submission/self-assignments/{assignmentId}")
    suspend fun getProblemRangeWithPage(
        @Path("assignmentId") assignmentId: Long
    ): List<ProblemsWithPage>

    @POST("/submission/self-assignments/{assignmentId}")
    suspend fun postAssignmentResult(
        @Path("assignmentId") assignmentId: Long,
        @Body wrongProblemWithinPageRequests: RequestAssignmentResultDto
    ): Response<Unit>
}