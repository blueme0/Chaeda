package com.chaeda.domain.repository

import java.io.File

interface SampleRepository {
    suspend fun getSample(): MutableList<Any>
    suspend fun getPresignedUrl(memberId: Int, imageType: String, imageFileExtension: String): Result<String>
    suspend fun putFileToUrl(url: String, contentType: String, file: File): Result<String>
}