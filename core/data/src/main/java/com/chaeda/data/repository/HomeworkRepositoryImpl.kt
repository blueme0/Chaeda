package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteSampleDataSource
import com.chaeda.domain.repository.HomeworkRepository
import javax.inject.Inject

class HomeworkRepositoryImpl @Inject constructor(
    private val remoteSampleDataSource: RemoteSampleDataSource
) : HomeworkRepository {

}