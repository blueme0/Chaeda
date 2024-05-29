package com.chaeda.domain.repository

import com.chaeda.domain.entity.Textbook

interface TextbookRepository {
    suspend fun getTextbooks(): Result<List<Textbook>>
}