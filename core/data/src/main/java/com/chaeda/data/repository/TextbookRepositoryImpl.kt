package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteTextbookDataSource
import com.chaeda.domain.repository.TextbookRepository
import javax.inject.Inject

class TextbookRepositoryImpl @Inject constructor(
    private val remoteTextbookDataSource: RemoteTextbookDataSource
) : TextbookRepository {

}