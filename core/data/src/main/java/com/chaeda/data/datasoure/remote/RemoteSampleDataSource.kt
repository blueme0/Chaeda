package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.data.service.ImageService
import com.chaeda.data.service.SampleService
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RemoteSampleDataSource @Inject constructor(
    private val sampleService: SampleService,
    private val imageService: ImageService
) {
    suspend fun getRecommendCourse(pageNo: String?): ResponseGetSample =
        sampleService.getSample()

    suspend fun getPresignedUrl(memberId: Int, imageInfo: ImageInfo): PresignedInfo =
        sampleService.getPresignedUrl(memberId, imageInfo).toPresignedInfo()

    suspend fun putFileToUrl(url: String, contentType: String, file: File): String =
        imageService.putFileToUrl(
            url,
            file.asRequestBody(contentType.toMediaTypeOrNull())
        )
}