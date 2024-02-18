package com.chaeda.data.service

import com.chaeda.data.model.response.PresignedResponse
import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.domain.entity.ImageInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

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
}