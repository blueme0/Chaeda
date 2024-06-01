package com.chaeda.data.service

import com.chaeda.data.model.request.textbook.RequestTextbookDto
import com.chaeda.data.model.response.presigned.ResponsePresignedTextbookDto
import com.chaeda.domain.entity.Textbook
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TextbookService {
    @GET("/textbook/list")
    suspend fun getTextbooks(): List<Textbook>

    @POST("/textbook/uploadCompleted")
    suspend fun postTextbookUploaded(
        @Body requestTextbook: RequestTextbookDto
    ): ResponseBody

    @POST("/textbook/presigned-url")
    suspend fun getPresignedUrlForTextbook(
        @Body requestTextbook: RequestTextbookDto
    ): ResponsePresignedTextbookDto
}