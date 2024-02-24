package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteSampleDataSource
import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import com.chaeda.domain.repository.SampleRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(private val remoteSampleDataSource: RemoteSampleDataSource) :
    SampleRepository {

    private val gsonBuilder: Gson = GsonBuilder().create()

    override suspend fun getSample(): MutableList<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getPresignedUrl(
        memberId: Int,
        imageType: String,
        imageFileExtension: String
    ): Result<PresignedInfo> {
        return runCatching {
            remoteSampleDataSource.getPresignedUrl(memberId, RequestImageInfo(imageType, imageFileExtension))
//            gsonBuilder.fromJson(presignedResponse, PresignedResponse::class.java).presignedUrl
        }
    }

    override suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String> {
        return runCatching {
            remoteSampleDataSource.putFileToUrl(url, contentType, file)
        }
    }

    override suspend fun uploadImages(images: List<FileWithName>): Result<String> {
        return runCatching {
            val imageParts = images.map { file ->
                val requestFile = file.image.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.fileName, requestFile)
            }
            remoteSampleDataSource.uploadImages(imageParts)
        }
    }

    override suspend fun noticePresignedUrl(
        memberId: Int,
        imageType: String,
        imageFileExtension: String,
        imageKey: String
    ) : Result<Any> {
        return runCatching {
            remoteSampleDataSource.noticePresignedUrl(memberId, ImageInfo(imageType, imageFileExtension, imageKey))
        }
    }

    override suspend fun getImagesUrl(memberId: Int, images: List<ImageInfo>): Result<List<String>> {
        return runCatching {
            remoteSampleDataSource.getImagesUrl(memberId, images)
        }
    }

}