package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.image.RequestImageInfoDto
import com.chaeda.data.service.AssignmentService
import com.chaeda.data.service.FileService
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val fileService: FileService,
    private val assignmentService: AssignmentService
) {
    suspend fun putFileToUrl(url: String, contentType: String, file: File): String =
        fileService.putFileToUrl(
            url,
            file.asRequestBody(contentType.toMediaTypeOrNull())
        ).string()

    suspend fun getPresignedUrl(requestImageInfo: RequestImageInfoDto): PresignedInfo =
        assignmentService.getPresignedUrl(requestImageInfo).toPresignedInfo()

    suspend fun uploadImages(images: List<MultipartBody.Part>): String =
        assignmentService.uploadImages(images)

    suspend fun noticePresignedUrl(imageInfo: ImageInfo): Any =
        assignmentService.noticePresignedUrl(listOf(imageInfo)).string()

    suspend fun getImagesUrl(images: List<ImageInfo>): List<String> =
        assignmentService.getImagesUrl(images)
}