package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.data.model.response.ResponseGetSample
import com.chaeda.data.service.ImageService
import com.chaeda.data.service.SampleService
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RemoteSampleDataSource @Inject constructor(
    private val sampleService: SampleService,
    private val imageService: ImageService
) {
    suspend fun getRecommendCourse(pageNo: String?): ResponseGetSample =
        sampleService.getSample()

    suspend fun getPresignedUrl(memberId: Int, requestImageInfo: RequestImageInfo): PresignedInfo =
        sampleService.getPresignedUrl(memberId, requestImageInfo).toPresignedInfo()

    suspend fun putFileToUrl(url: String, contentType: String, file: File): String =
        imageService.putFileToUrl(
            url,
            file.asRequestBody(contentType.toMediaTypeOrNull())
        ).string()

    suspend fun uploadImages(images: List<MultipartBody.Part>): String =
        sampleService.uploadImages(images)

    suspend fun noticePresignedUrl(memberId: Int, imageInfo: ImageInfo): Any =
        sampleService.noticePresignedUrl(memberId, imageInfo).string()
}