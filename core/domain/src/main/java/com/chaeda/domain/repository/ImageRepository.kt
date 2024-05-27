package com.chaeda.domain.repository

import com.chaeda.domain.entity.FileWithName
import com.chaeda.domain.entity.ImageInfo
import com.chaeda.domain.entity.PresignedInfo
import java.io.File

interface ImageRepository {
    suspend fun getPresignedUrl(imageType: String, imageFileExtension: String): Result<PresignedInfo>
    suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String>
    suspend fun uploadImages(images: List<FileWithName>): Result<String>
    suspend fun noticePresignedUrl(imageType: String, imageFileExtension: String, imageKey: String): Result<Any>
    suspend fun getImagesUrl(images: List<ImageInfo>): Result<List<String>>
}