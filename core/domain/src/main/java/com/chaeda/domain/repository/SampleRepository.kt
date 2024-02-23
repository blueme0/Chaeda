package com.chaeda.domain.repository

import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.PresignedInfo
import java.io.File

interface SampleRepository {
    suspend fun getSample(): MutableList<Any>
    suspend fun getPresignedUrl(memberId: Int, imageType: String, imageFileExtension: String): Result<PresignedInfo>
    suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String>
    suspend fun uploadImages(images: List<FileWithName>): Result<String>
}