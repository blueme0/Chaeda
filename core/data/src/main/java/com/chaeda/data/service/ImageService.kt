package com.chaeda.data.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface ImageService {
    @PUT
    suspend fun putFileToUrl(
        @Url url: String,
        @Body requestBody: RequestBody
    ): ResponseBody
}