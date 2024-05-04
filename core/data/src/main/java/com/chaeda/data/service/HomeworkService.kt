package com.chaeda.data.service

import com.chaeda.data.model.request.RequestAssignmentDTO
import com.chaeda.data.model.request.RequestAssignmentResultDTO
import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.data.model.response.PresignedResponse
import com.chaeda.domain.entity.AssignmentDTO
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.ProblemsWithPageDTO
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

interface HomeworkService {

    /**
     * api related to images
     */

    @POST("/api/images/presigned-url/{memberId}")
    suspend fun getPresignedUrl(
        @Path("memberId") memberId: Int,
        @Body requestImageInfo: RequestImageInfo
    ): PresignedResponse

    @Multipart
    @POST("/api/images/upload") // 실제 API 엔드포인트를 여기에 입력
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): String

    @POST("/api/images/presigned-url/complete/{memberId}")
    suspend fun noticePresignedUrl(
        @Path("memberId") memberId: Int,
        @Body imageInfo: ImageInfo
    ): ResponseBody

    @POST("/api/images/display/{memberId}")
    suspend fun getImagesUrl(
        @Path("memberId") memberId: Int,
        @Body images: List<ImageInfo>
    ): List<String>

    /**
     * api related to assignment
     */

    @GET("/assignment/{assignmentId}")
    suspend fun getAssignmentById(
        @Path("assignmentId") assignmentId: Int
    ): AssignmentDTO

    @PUT("/assignment/{assignmentId}")
    suspend fun putAssignmentById(
        @Path("assignmentId") assignmentId: Int,
        @Body assignment: RequestAssignmentDTO
    ): AssignmentDTO

    @DELETE("/assignment/{assignmentId}")
    suspend fun deleteAssignmentById(
        @Path("assignmentId") assignmentId: Int
    ): Response<Unit>

    @GET("/assignment")
    suspend fun getAssignmentsByDate(
        @Query("date") date: String
//        @Named("date") @Body d: LocalDate
    ): List<AssignmentDTO>

    @POST("/assignment")
    suspend fun postAssignment(
        @Body request: RequestAssignmentDTO
    ): AssignmentDTO

    @GET("/submission/self-assignments/{assignmentId}")
    suspend fun getProblemRangeWithPage(
        @Path("assignmentId") assignmentId: Int
    ): List<ProblemsWithPageDTO>

    @POST("/submission/self-assignments/{assignmentId}")
    suspend fun postAssignmentResult(
        @Path("assignmentId") assignmentId: Int,
        @Body wrongProblemWithinPageRequests: RequestAssignmentResultDTO
    ): Response<Unit>
}