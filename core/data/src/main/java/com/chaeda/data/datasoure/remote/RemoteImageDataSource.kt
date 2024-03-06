package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.data.service.HomeworkService
import com.chaeda.data.service.ImageService
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val imageService: ImageService,
    private val homeworkService: HomeworkService
) {
    suspend fun putFileToUrl(url: String, contentType: String, file: File): String =
        imageService.putFileToUrl(
            url,
            file.asRequestBody(contentType.toMediaTypeOrNull())
        ).string()

    suspend fun getPresignedUrl(memberId: Int, requestImageInfo: RequestImageInfo): PresignedInfo =
        homeworkService.getPresignedUrl(memberId, requestImageInfo).toPresignedInfo()

    suspend fun uploadImages(images: List<MultipartBody.Part>): String =
        homeworkService.uploadImages(images)

    suspend fun noticePresignedUrl(memberId: Int, imageInfo: ImageInfo): Any =
        homeworkService.noticePresignedUrl(memberId, imageInfo).string()

    suspend fun getImagesUrl(memberId: Int, images: List<ImageInfo>): List<String> =
        homeworkService.getImagesUrl(memberId, images)
}