package com.chaeda.data.service

import com.chaeda.domain.entity.Textbook
import retrofit2.http.GET

interface TextbookService {
    @GET("/textbook/list")
    suspend fun getTextbooks(): List<Textbook>
}