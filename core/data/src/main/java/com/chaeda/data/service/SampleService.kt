package com.chaeda.data.service

import com.chaeda.data.model.response.PresignedResponse
import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.domain.entity.ImageInfo
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SampleService {
    @GET("/endpoint")
    suspend fun getSample(
    ): ResponseGetSample

//    @POST("/endpoint")
//    suspend fun postSample(
//        @Body requestSample: RequestSample,
//    ): ResponsePostSample
//
//    @Multipart
//    @POST("/endpoint")
//    suspend fun postMultiPartSample(
//        @Part image: MultipartBody.Part,
//        @Part("data") data: RequestBody,
//    ): Response<ResponsePostMultiPartSampleDTO>

    @POST("/api/images/presigned-url/{memberId}")
    suspend fun getPresignedUrl(
        @Path ("memberId") memberId: Int,
        @Body imageInfo: ImageInfo
    ): PresignedResponse

    @Multipart
    @POST("/api/images/upload") // 실제 API 엔드포인트를 여기에 입력
    fun uploadImages(
        @Part images: List<MultipartBody.Part>
    ): String
}