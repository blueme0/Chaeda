package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteTextbookDataSource
import com.chaeda.domain.entity.TextbookDTO
import com.chaeda.domain.repository.TextbookRepository
import javax.inject.Inject

class TextbookRepositoryImpl @Inject constructor(
    private val remoteTextbookDataSource: RemoteTextbookDataSource
) : TextbookRepository {
    override suspend fun getTextbooks(): Result<List<TextbookDTO>> {
        return runCatching {
            remoteTextbookDataSource.getTextbooks()
        }
    }

}