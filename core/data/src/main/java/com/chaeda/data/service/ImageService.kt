package com.chaeda.data.service

import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url

interface ImageService {
    @PUT
    suspend fun putFileToUrl(
        @Url url: String,
        @Body requestBody: RequestBody
    ): String
}