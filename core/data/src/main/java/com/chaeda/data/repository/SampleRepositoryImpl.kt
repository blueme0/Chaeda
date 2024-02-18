package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteSampleDataSource
import com.chaeda.data.model.response.PresignedResponse
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import com.chaeda.domain.repository.SampleRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import timber.log.Timber
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
            remoteSampleDataSource.getPresignedUrl(memberId, ImageInfo(imageType, imageFileExtension))
//            gsonBuilder.fromJson(presignedResponse, PresignedResponse::class.java).presignedUrl
        }
    }

    override suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String> {
        return runCatching {
            remoteSampleDataSource.putFileToUrl(url, contentType, file)
        }
    }

}