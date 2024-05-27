package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteImageDataSource
import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import com.chaeda.domain.repository.ImageRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteImageDataSource: RemoteImageDataSource
) : ImageRepository {

    private val gsonBuilder: Gson = GsonBuilder().create()

    override suspend fun getPresignedUrl(
        imageType: String,
        imageFileExtension: String
    ): Result<PresignedInfo> {
        return runCatching {
            remoteImageDataSource.getPresignedUrl(RequestImageInfo(imageType, imageFileExtension))
//            gsonBuilder.fromJson(presignedResponse, PresignedResponse::class.java).presignedUrl
        }
    }

    override suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String> {
        return runCatching {
            remoteImageDataSource.putFileToUrl(url, contentType, file)
        }
    }

    override suspend fun uploadImages(images: List<FileWithName>): Result<String> {
        return runCatching {
            val imageParts = images.map { file ->
                val requestFile = file.image.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.fileName, requestFile)
            }
            remoteImageDataSource.uploadImages(imageParts)
        }
    }

    override suspend fun noticePresignedUrl(
        imageType: String,
        imageFileExtension: String,
        imageKey: String
    ) : Result<Any> {
        return runCatching {
            remoteImageDataSource.noticePresignedUrl(ImageInfo(imageType, imageFileExtension, imageKey))
        }
    }

    override suspend fun getImagesUrl(images: List<ImageInfo>): Result<List<String>> {
        return runCatching {
            remoteImageDataSource.getImagesUrl(images)
        }
    }

}