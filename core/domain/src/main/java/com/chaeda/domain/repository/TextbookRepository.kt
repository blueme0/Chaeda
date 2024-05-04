package com.chaeda.domain.repository

import com.chaeda.domain.entity.TextbookDTO

interface TextbookRepository {
    suspend fun getTextbooks(): Result<List<TextbookDTO>>
}