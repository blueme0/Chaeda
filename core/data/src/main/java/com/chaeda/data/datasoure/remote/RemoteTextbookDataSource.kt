package com.chaeda.data.datasoure.remote

import com.chaeda.data.service.TextbookService
import com.chaeda.domain.entity.Textbook
import javax.inject.Inject

class RemoteTextbookDataSource @Inject constructor(
    private val textbookService: TextbookService
) {
    suspend fun getTextbooks(): List<Textbook> {
        return textbookService.getTextbooks()
    }
}