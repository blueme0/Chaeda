package com.chaeda.data.repository

import com.chaeda.data.datasoure.remote.RemoteSampleDataSource
import com.chaeda.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val remoteSampleDataSource: RemoteSampleDataSource
) : SampleRepository {
    override suspend fun getSample(): MutableList<Any> {
        TODO("Not yet implemented")
    }
}