package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteTextbookDataSource
import com.chaeda.domain.entity.Textbook
import com.chaeda.domain.repository.TextbookRepository
import javax.inject.Inject

class TextbookRepositoryImpl @Inject constructor(
    private val remoteTextbookDataSource: RemoteTextbookDataSource
) : TextbookRepository {
    override suspend fun getTextbooks(): Result<List<Textbook>> {
        return runCatching {
            remoteTextbookDataSource.getTextbooks()
        }
    }

    override suspend fun getPresignedUrlForTextbook(textbook: Textbook): Result<String> {
        return runCatching {
            remoteTextbookDataSource.getPresignedUrlForTextbook(textbook)
        }
    }

    override suspend fun postTextbookUploaded(textbook: Textbook): Result<Any> {
        return runCatching {
            remoteTextbookDataSource.postTextbookUploaded(textbook)
        }
    }

}