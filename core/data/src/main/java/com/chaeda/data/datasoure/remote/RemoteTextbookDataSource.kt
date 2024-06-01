package com.chaeda.data.datasoure.remote

import com.chaeda.data.model.request.textbook.RequestTextbookDto
import com.chaeda.data.service.TextbookService
import com.chaeda.domain.entity.Textbook
import javax.inject.Inject

class RemoteTextbookDataSource @Inject constructor(
    private val textbookService: TextbookService
) {
    suspend fun getTextbooks(): List<Textbook> {
        return textbookService.getTextbooks()
    }

    suspend fun postTextbookUploaded(textbook: Textbook): Any {
        return textbookService.postTextbookUploaded(
            RequestTextbookDto(
                textbook.name,
                textbook.startPage,
                textbook.lastPage,
                textbook.publisher,
                textbook.targetGrade,
                textbook.subject,
                textbook.publishYear,
                "pdf"
            )
        )
    }

    suspend fun getPresignedUrlForTextbook(textbook: Textbook): String {
        return textbookService.getPresignedUrlForTextbook(
            RequestTextbookDto(
                textbook.name,
                textbook.startPage,
                textbook.lastPage,
                textbook.publisher,
                textbook.targetGrade,
                textbook.subject,
                textbook.publishYear,
                "pdf"
            )
        ).presignedUrl
    }
}