package com.chaeda.domain.repository

import com.chaeda.domain.entity.Textbook

interface TextbookRepository {
    suspend fun getTextbooks(): Result<List<Textbook>>
    suspend fun postTextbookUploaded(textbook: Textbook): Result<Any>
    suspend fun getPresignedUrlForTextbook(textbook: Textbook): Result<String>
}